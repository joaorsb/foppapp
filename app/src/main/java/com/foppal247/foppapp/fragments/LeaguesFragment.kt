package com.foppal247.foppapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.foppal247.foppapp.domain.LeagueTypes
import com.foppal247.foppapp.R
import com.foppal247.foppapp.adapter.NewsAdapter
import com.foppal247.foppapp.domain.News
import com.foppal247.foppapp.domain.NewsService
import kotlinx.android.synthetic.main.fragment_leagues.*
import android.content.Intent
import android.net.Uri
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.utils.AndroidUtils
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread


class LeaguesFragment : BaseFragment() {
    private var leagueType: LeagueTypes? = LeagueTypes.eliteserien
    private var newsList = listOf<News>()

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              icicle: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_leagues, container, false)
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
        swipeFragment.setOnRefreshListener { taskNews() }
        swipeFragment.setColorSchemeColors(
            R.color.refresh_progress_1,
            R.color.refresh_progress_2,
            R.color.refresh_progress_3
        )
        taskNews()
    }

    override fun onDestroy() {
        super.onDestroy()
        newsList = listOf()
    }

    private fun taskNews(){
        if(AndroidUtils.isNetworkAvailable(context)){
            doAsync {
                swipeFragment.isRefreshing = ! swipeFragment.isRefreshing

                if(FoppalApplication.getInstance().league?.string == R.string.all) {
                    newsList = NewsService.getAllNews(context)
                } else {
                    newsList = NewsService.getLeagueNews(context)
                }
                uiThread {
                    recyclerView.adapter = NewsAdapter(newsList) { onClickNews(it)}
                    swipeFragment.isRefreshing = false

                }
            }
        } else {
            var errorMessage = context?.getText(R.string.noInternet)
            toast(errorMessage!!)
        }

    }

    fun onClickNews(news: News) {
        val url = news.url
        val intentToBrowser = Intent(Intent.ACTION_VIEW)
        intentToBrowser.data = Uri.parse(url)
        startActivity(intentToBrowser)
    }
}