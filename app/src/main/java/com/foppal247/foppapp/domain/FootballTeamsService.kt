package com.foppal247.foppapp.domain

import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.domain.dao.FavoriteTeamsDatabaseManager
import com.foppal247.foppapp.domain.dao.FootballTeamsDatabaseManager
import com.foppal247.foppapp.domain.model.FavoriteTeam
import com.foppal247.foppapp.domain.model.FootballTeam
import com.foppal247.foppapp.domain.model.Team
import com.foppal247.foppapp.extensions.fromJson
import com.foppal247.foppapp.utils.HttpHelper
import com.foppal247.foppapp.utils.LeagueHelper
import org.jetbrains.anko.doAsync

object FootballTeamsService {
    private const val TAG = "FootballTeamsService"

    fun getFootballTeams(): MutableList<FootballTeam> {
        val dao = FootballTeamsDatabaseManager.getFootballTeamsDAO()
        return dao.findAll()
    }

    fun getFootballTeamsByLeague(leagueId: Int?): MutableList<FootballTeam> {
        val dao = FootballTeamsDatabaseManager.getFootballTeamsDAO()
        return dao.getFootballTeamsByLeague(leagueId)
    }

    fun deleteFootballTeam(intlName: String) : Boolean{
        val dao = FootballTeamsDatabaseManager.getFootballTeamsDAO()
        val team = dao.getByIntlName(intlName)
        if (team != null){
            dao.delete(team)
            return true
        }
        return false
    }

    fun deleteAllFootballTeams() {
        val dao = FootballTeamsDatabaseManager.getFootballTeamsDAO()
        dao.deleteAll()
    }

    fun deleteAllFootballTeamsByLeague(league: Int) {
        val dao = FootballTeamsDatabaseManager.getFootballTeamsDAO()
        val teams = dao.getFootballTeamsByLeague(league)
        teams.forEach {
            deleteFootballTeam(it.intlName)
        }
    }

    fun getFootballTeamsByLeagueREST() : List<Team>{
        val country = FoppalApplication.getInstance().country
        val leagueName = LeagueHelper.getLeagueName()
        val mainURL = "https://www.foppal247.com/$country/api/league_list/$leagueName/"
        val json = HttpHelper.get(mainURL)
        val footballTeams = fromJson<List<Team>>(json)
        return footballTeams
    }
}