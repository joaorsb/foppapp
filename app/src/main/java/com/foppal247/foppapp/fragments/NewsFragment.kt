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




class NewsFragment : BaseFragment() {
    private var newsList = listOf<News>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              icicle: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        return view

    }

    override fun onViewCreated(view: View, icicle: Bundle?) {
        super.onViewCreated(view, icicle)
        super.checkConnection()
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
            recyclerView.adapter = NewsAdapter(FoppalApplication.getInstance().newsList) { onClickNews(it) }
            swipeFragment.isRefreshing = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        newsList = listOf()
        FoppalApplication.getInstance().selectedIntlTeamName = ""
        FoppalApplication.getInstance().selectedTeamName = ""
    }

    private fun taskNews(){
        doAsync {
            swipeFragment.isRefreshing = !swipeFragment.isRefreshing

            if (FoppalApplication.getInstance().selectedIntlTeamName.isNullOrBlank()) {
                if (FoppalApplication.getInstance().league?.leagueName == R.string.all) {
                    FoppalApplication.getInstance().newsList = NewsService.getAllNews()
                } else {
                    FoppalApplication.getInstance().newsList = NewsService.getLeagueNews()
                }

            } else {
                FoppalApplication.getInstance().newsList = NewsService.getTeamNews()
            }
            uiThread {
                recyclerView.adapter = NewsAdapter(FoppalApplication.getInstance().newsList) { onClickNews(it) }
                swipeFragment.isRefreshing = false
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