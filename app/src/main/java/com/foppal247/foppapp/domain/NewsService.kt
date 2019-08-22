package com.foppal247.foppapp.domain

import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.R
import com.foppal247.foppapp.domain.model.News
import com.foppal247.foppapp.extensions.fromJson
import com.foppal247.foppapp.utils.HttpHelper
import com.foppal247.foppapp.utils.LeagueHelper
import java.net.URL

object NewsService {
    private const val TAG = "NewsService"
    private var page: Int = 1
    fun getAllNews(){
        val country = FoppalApplication.getInstance().country
        val mainURL: String
        page = FoppalApplication.getInstance().pageNumber
        if(FoppalApplication.getInstance().englishNews){
            mainURL = "https://www.foppal247.com/$country/api/language/en?page=$page"
        } else {
            mainURL = "https://www.foppal247.com/$country/api/?page=$page"
        }
        val json = HttpHelper.get(mainURL)
        if(page == 1){
            FoppalApplication.getInstance().newsList = fromJson(json)
        } else {
            FoppalApplication.getInstance().newsList.addAll(fromJson(json))
        }
    }

    fun getLeagueNews(){
        val leagueName = LeagueHelper.getLeagueName()
        val country = FoppalApplication.getInstance().country
        val leagueUrl = "https://www.foppal247.com/$country/api/leagues/$leagueName?page=1"
        val json = URL(leagueUrl).readText()
        FoppalApplication.getInstance().newsList = fromJson(json)
    }

    fun getTeamNews(){
        val country = FoppalApplication.getInstance().country
        val teamName = FoppalApplication.getInstance().selectedIntlTeamName
        val leagueUrl = "https://www.foppal247.com/$country/api/teams/$teamName?page=1"
        val json = URL(leagueUrl).readText()
        FoppalApplication.getInstance().newsList = fromJson(json)
    }

    fun getNews() {
        if (FoppalApplication.getInstance().selectedIntlTeamName.isNullOrBlank()) {
            if (FoppalApplication.getInstance().league?.leagueName == R.string.all) {
                getAllNews()
            } else {
                getLeagueNews()
            }
        } else {
            getTeamNews()
        }
    }

    fun getMoreAllNews() : List<News> {
        val country = FoppalApplication.getInstance().country
        val mainURL: String
        page = FoppalApplication.getInstance().pageNumber
        if(FoppalApplication.getInstance().englishNews){
            mainURL = "https://www.foppal247.com/$country/api/language/en?page=$page"
        } else {
            mainURL = "https://www.foppal247.com/$country/api/?page=$page"
        }
        val json = HttpHelper.get(mainURL)
         return fromJson(json)

    }

    fun getMoreLeagueNews(): List<News> {
        val leagueName = LeagueHelper.getLeagueName()
        val country = FoppalApplication.getInstance().country
        val page = FoppalApplication.getInstance().pageNumber
        val leagueUrl = "https://www.foppal247.com/$country/api/leagues/$leagueName?page=$page"
        val json = URL(leagueUrl).readText()
        return fromJson(json)
    }

    fun getMoreTeamNews() : List<News> {
        val country = FoppalApplication.getInstance().country
        val teamName = FoppalApplication.getInstance().selectedIntlTeamName
        val page = FoppalApplication.getInstance().pageNumber
        val leagueUrl = "https://www.foppal247.com/$country/api/teams/$teamName?page=$page"
        val json = URL(leagueUrl).readText()
        return fromJson(json)
    }

    fun getMoreNews(): List<News> {
        var listNews: List<News>
        if (FoppalApplication.getInstance().selectedIntlTeamName.isNullOrBlank()) {
            if (FoppalApplication.getInstance().league?.leagueName == R.string.all) {
                listNews = getMoreAllNews()
            } else {
                listNews = getMoreLeagueNews()
            }
        } else {
            listNews = getMoreTeamNews()
        }
        return listNews
    }

}