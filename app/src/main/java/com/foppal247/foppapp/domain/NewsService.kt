package com.foppal247.foppapp.domain

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
        val mainURL: String
        if(FoppalApplication.getInstance().englishNews){
            mainURL = "https://www.foppal247.com/$country/api/language/en?page=1"
        } else {
            mainURL = "https://www.foppal247.com/$country/api/?page=1"
        }
        val json = HttpHelper.get(mainURL)
        val news = fromJson<List<News>>(json)

        return news
    }

    fun getLeagueNews() : List<News>{
        val leagueName = LeagueHelper.getLeagueName()
        val country = FoppalApplication.getInstance().country
        val leagueUrl = "https://www.foppal247.com/${country}/api/leagues/$leagueName?page=1"
        val json = URL(leagueUrl).readText()
        val news = fromJson<List<News>>(json)

        return news
    }

    fun getTeamNews() : List<News>{
        val country = FoppalApplication.getInstance().country
        val teamName = FoppalApplication.getInstance().selectedIntlTeamName
        val leagueUrl = "https://www.foppal247.com/${country}/api/teams/$teamName?page=1"
        val json = URL(leagueUrl).readText()
        val news = fromJson<List<News>>(json)

        return news
    }

}