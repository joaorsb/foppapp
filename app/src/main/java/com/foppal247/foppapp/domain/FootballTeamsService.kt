package com.foppal247.foppapp.domain

import android.util.Log
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.domain.dao.DatabaseManager
import com.foppal247.foppapp.domain.model.FootballTeam
import com.foppal247.foppapp.domain.model.Team
import com.foppal247.foppapp.extensions.fromJson
import com.foppal247.foppapp.utils.HttpHelper
import com.foppal247.foppapp.utils.LeagueHelper

object FootballTeamsService {
    private const val TAG = "FootballTeamsService"

    fun getFootballTeams(): List<FootballTeam> {
        val dao = DatabaseManager.getFootballTeamsDAO()
        return dao.findAll()
    }

    fun deleteFootballTeam(intlName: String) : Boolean{
        val dao = DatabaseManager.getFootballTeamsDAO()
        val team = dao.getByIntlName(intlName)
        if (team != null){
            dao.delete(team)
            return true
        }
        return false
    }

    fun getFootballTeamsByLeagueREST() : List<Team>{
        val country = CountryService.getAppCountries(FoppalApplication.getInstance().applicationContext)
        val leagueName = LeagueHelper.getLeagueName()
        val mainURL = "https://www.foppal247.com/$country/api/league_list/$leagueName/"
//        val mainURL = "https://www.foppal247.com/brazil/api/league_list/brasileirao/"
        val json = HttpHelper.get(mainURL)
        val footballTeams = fromJson<List<Team>>(json)
        Log.d(TAG, leagueName)
        Log.d(TAG, country)
        return footballTeams
    }
}