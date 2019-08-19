package com.foppal247.foppapp.domain

import android.content.Context
import android.util.Log
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.domain.model.News
import com.foppal247.foppapp.extensions.fromJson
import com.foppal247.foppapp.utils.HttpHelper
import com.foppal247.foppapp.utils.LeagueHelper
import java.net.URL

object NewsService {
    private const val TAG = "NewsService"
    fun getAllNews() : List<News>{
        val country = FoppalApplication.getInstance().country
        val mainURL = "https://www.foppal247.com/$country/api/?page=1"
        Log.d(TAG, mainURL)
        val json = HttpHelper.get(mainURL)
        val news = fromJson<List<News>>(json)
        Log.d(TAG, "${news.size} links found")

        return news
    }

    fun getLeagueNews() : List<News>{
        var leagueName: String
        val country = FoppalApplication.getInstance().country

        leagueName = LeagueHelper.getLeagueName()
        val leagueUrl = "https://www.foppal247.com/${country}/api/leagues/$leagueName?page=1"
        Log.d(TAG, leagueUrl)
        val json = URL(leagueUrl).readText()
        val news = fromJson<List<News>>(json)
        Log.d(TAG, "${news.size} links found")

        return news
    }

    fun getTeamNews() : List<News>{
        val country = FoppalApplication.getInstance().country
        val teamName = FoppalApplication.getInstance().selectedIntlTeamName
        val leagueUrl = "https://www.foppal247.com/${country}/api/teams/$teamName?page=1"
        Log.d(TAG, leagueUrl)
        val json = URL(leagueUrl).readText()
        val news = fromJson<List<News>>(json)
        Log.d(TAG, "${news.size} links found")

        return news
    }

}