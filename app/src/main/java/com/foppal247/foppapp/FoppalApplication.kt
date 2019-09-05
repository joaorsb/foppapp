package com.foppal247.foppapp

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.foppal247.foppapp.domain.model.FavoriteTeam
import com.foppal247.foppapp.domain.model.FootballTeam
import com.foppal247.foppapp.domain.model.LeagueTypes
import com.foppal247.foppapp.domain.model.News

class FoppalApplication : Application() {
    var league: LeagueTypes? = LeagueTypes.all
    var country: String = "norge"
    var footballTeams: MutableList<FootballTeam> = mutableListOf()
    var favoriteTeams: MutableList<FavoriteTeam> = mutableListOf()
    var newsList: MutableList<News> = mutableListOf()
    var selectedIntlTeamName: String = ""
    var selectedTeamName: String = ""
    var menuGroupId = 0
    var pageNumber = 1
    var englishNews: Boolean = false


    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    private fun clearAllData(){
        selectedIntlTeamName = ""
        selectedTeamName = ""
        footballTeams = mutableListOf()
        newsList = mutableListOf()
        englishNews = false
    }

    companion object {
        private var appInstance: FoppalApplication? = null
        fun getInstance(): FoppalApplication {
            checkNotNull(appInstance) { "Configure a classe de Application no AndroidManifest.xml" }
            return appInstance!!
        }
    }


    override fun onTerminate() {
        super.onTerminate()
        clearAllData()
    }
}
