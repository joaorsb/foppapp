package com.foppal247.foppapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.foppal247.foppapp.R
import com.foppal247.foppapp.adapter.NewsAdapter
import com.foppal247.foppapp.domain.model.News
import kotlinx.android.synthetic.main.fragment_news.*
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.foppal247.foppapp.FoppalApplication
import androidx.recyclerview.widget.RecyclerView
import com.foppal247.foppapp.viewModels.NewsViewModel
import org.jetbrains.anko.support.v4.toast


class NewsFragment : BaseFragment() {
    val adapter = NewsAdapter(FoppalApplication.getInstance().newsList) { onClickNews(it) }
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              icicle: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        return view

    }

    override fun onViewCreated(view: View, icicle: Bundle?) {
        super.onViewCreated(view, icicle)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        newsViewModel.newsList.observe(this, Observer { newsList ->
            if(newsViewModel.page.value == 1){
                adapter.setNewsData(newsList)
            } else {
                adapter.addNewsData(newsList)
            }
            swipeFragment.isRefreshing = false
        })

    }

    @SuppressLint("ResourceAsColor")
    override fun onResume() {
        super.onResume()
        swipeFragment.setOnRefreshListener { newsViewModel.setPage(1)}
        swipeFragment.setColorSchemeColors(
            R.color.refresh_progress_1,
            R.color.refresh_progress_2,
            R.color.refresh_progress_3
        )

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkConnection()
        if(super.hasConnection) {
            swipeFragment.isRefreshing = true
            if(FoppalApplication.getInstance().selectedIntlTeamName.isNotEmpty()) {
                newsViewModel.setTeam(FoppalApplication.getInstance().selectedIntlTeamName)
            }
            newsViewModel.setLeague(FoppalApplication.getInstance().league?.leagueName as Int)
            newsViewModel.setEnglishNews(FoppalApplication.getInstance().englishNews)
            newsViewModel.setCountry(FoppalApplication.getInstance().country)
            newsViewModel.setPage(FoppalApplication.getInstance().pageNumber)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = (recyclerView.layoutManager as LinearLayoutManager).childCount
                val totalItemCount = (recyclerView.layoutManager as LinearLayoutManager).itemCount
                val firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                    if (!swipeFragment.isRefreshing && hasConnection){
                        toast(getString(R.string.getting_more_news))
                        swipeFragment.isRefreshing = true
                        val page = newsViewModel.page.value?.plus(1)
                        newsViewModel.setPage(page as Int)
                    }
                }
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        FoppalApplication.getInstance().selectedIntlTeamName = ""
        FoppalApplication.getInstance().selectedTeamName = ""
        FoppalApplication.getInstance().pageNumber = 1
        newsViewModel.cancelJobs()
    }

    private fun onClickNews(news: News) {
        val url = news.url
        val intentToBrowser = Intent(Intent.ACTION_VIEW)
        intentToBrowser.data = Uri.parse(url)
        startActivity(intentToBrowser)
    }

}