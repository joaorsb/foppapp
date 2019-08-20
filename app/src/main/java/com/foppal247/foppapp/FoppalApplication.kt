package com.foppal247.foppapp

import android.app.Application
import com.foppal247.foppapp.domain.model.FootballTeam
import com.foppal247.foppapp.domain.model.LeagueTypes
import com.foppal247.foppapp.domain.model.News
import java.lang.IllegalStateException

class FoppalApplication : Application() {
    private val TAG = "FoppalApplication"
    var league: LeagueTypes? = LeagueTypes.all
    var country: String? = "norge"
    var footballTeams: MutableList<FootballTeam> = mutableListOf()
    var newsList: List<News> = listOf()
    var selectedIntlTeamName: String? = ""
    var selectedTeamName: String? = ""
    var menuGroupId = 0
    var englishNews: Boolean = false
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    fun clearAllData(){
        selectedIntlTeamName = ""
        selectedTeamName = ""
        footballTeams = mutableListOf()
        newsList = listOf()
        englishNews = false
    }

    companion object {
        private var appInstance: FoppalApplication? = null
        fun getInstance(): FoppalApplication {
            if (appInstance == null) {
                throw IllegalStateException("Configure a classe de Application no AndroidManifest.xml")
            }
            return appInstance!!
        }
    }


    override fun onTerminate() {
        super.onTerminate()
    }
}
