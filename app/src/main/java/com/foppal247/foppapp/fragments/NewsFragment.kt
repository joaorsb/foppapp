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
import com.foppal247.foppapp.domain.NewsService
import kotlinx.android.synthetic.main.fragment_news.*
import android.content.Intent
import android.net.Uri
import com.foppal247.foppapp.FoppalApplication
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import androidx.recyclerview.widget.RecyclerView
import com.foppal247.foppapp.utils.AndroidUtils
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.toast


class NewsFragment : BaseFragment() {
    private var newsList = listOf<News>()
    private var isLoading = false
    var checkHasConnection: Boolean = false
    val adapter = NewsAdapter { onClickNews(it) }

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

    }

    @SuppressLint("ResourceAsColor")
    override fun onResume() {
        super.onResume()
        swipeFragment.setOnRefreshListener { if(hasConnection) taskNews()}
        swipeFragment.setColorSchemeColors(
            R.color.refresh_progress_1,
            R.color.refresh_progress_2,
            R.color.refresh_progress_3
        )

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkConnection()
        if(super.hasConnection && FoppalApplication.getInstance().newsList.isEmpty()) {
            taskNews()
        } else {
            recyclerView.adapter = NewsAdapter { onClickNews(it) }
            swipeFragment.isRefreshing = false
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = (recyclerView.layoutManager as LinearLayoutManager).childCount
                val totalItemCount = (recyclerView.layoutManager as LinearLayoutManager).itemCount
                val firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                // Load more if we have reach the end to the recyclerView
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                    if (!isLoading && AndroidUtils.isNetworkAvailable(context)){
                        isLoading = true
                        toast("Loading more news!")
                        swipeFragment.isRefreshing = true
                        Thread {
                            //Do some Network Request
                            FoppalApplication.getInstance().pageNumber++
                            val newsList = NewsService.getMoreNews()
                            runOnUiThread {
                                var itemCount = adapter.itemCount
                                newsList.forEach {
                                    adapter.news.add(itemCount, it)
                                    adapter.notifyDataSetChanged()
                                    itemCount++
                                }
                            }
                            isLoading = false
                        }.start()
                        swipeFragment.isRefreshing = false
                    }
                }
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        newsList = listOf()
        FoppalApplication.getInstance().selectedIntlTeamName = ""
        FoppalApplication.getInstance().selectedTeamName = ""
        FoppalApplication.getInstance().pageNumber = 1
    }

    private fun taskNews(){
        doAsync {
            swipeFragment.isRefreshing = !swipeFragment.isRefreshing
            NewsService.getNews()
            uiThread {
                adapter.news = FoppalApplication.getInstance().newsList
                recyclerView.adapter = adapter
                swipeFragment.isRefreshing = false
                isLoading = false
            }
        }
    }

    fun onClickNews(news: News) {
        val url = news.url
        val intentToBrowser = Intent(Intent.ACTION_VIEW)
        intentToBrowser.data = Uri.parse(url)
        startActivity(intentToBrowser)
    }

}