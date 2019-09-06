package com.foppal247.foppapp.domain

import androidx.lifecycle.LiveData
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.R
import com.foppal247.foppapp.domain.dao.FootballTeamsDatabaseManager
import com.foppal247.foppapp.domain.model.FootballTeam
import com.foppal247.foppapp.domain.model.News
import com.foppal247.foppapp.domain.model.Team
import com.foppal247.foppapp.extensions.fromJson
import com.foppal247.foppapp.utils.HttpHelper
import com.foppal247.foppapp.utils.LeagueHelper
import kotlinx.coroutines.*

object FootballTeamsRepository {
    val BASE_URL = "https://foppal247.com/"
    var job: CompletableJob? = null
    private val dao = FootballTeamsDatabaseManager.getFootballTeamsDAO()

    fun getLocalFootballTeams(): LiveData<MutableList<FootballTeam>> {
        job = Job()
        return object : LiveData<MutableList<FootballTeam>>() {
            override fun onActive() {
                super.onActive()
                job?.let { thisJob ->
                    CoroutineScope(Dispatchers.IO + thisJob).launch {
                        val teams = dao.findAll()
                        withContext(Dispatchers.Main) {
                            value = teams
                            thisJob.complete()
                        }
                    }
                }
            }
        }
    }

    fun getLocalFootballTeamsByLeague(leagueId: Int?): LiveData<MutableList<FootballTeam>> {
        job = Job()
        return object : LiveData<MutableList<FootballTeam>>() {
            override fun onActive() {
                super.onActive()
                job?.let { thisJob ->
                    CoroutineScope(Dispatchers.IO + thisJob).launch {
                        val teams = dao.getFootballTeamsByLeague(leagueId)
                        withContext(Dispatchers.Main) {
                            value = teams
                            thisJob.complete()
                        }
                    }
                }
            }
        }
    }

    fun getDeleteFootballTeamsByName(intlName: String): LiveData<Boolean> {
        job = Job()
        return object : LiveData<Boolean>() {
            override fun onActive() {
                super.onActive()
                job?.let { thisJob ->
                    CoroutineScope(Dispatchers.IO + thisJob).launch {
                        val team = dao.getByIntlName(intlName)
                        withContext(Dispatchers.Main) {
                            var result = false
                            if (team != null){
                                dao.delete(team)
                                result = false
                            }
                            value = result
                            thisJob.complete()
                        }
                    }
                }
            }
        }
    }

    fun getApiFootballTeams(country: String?, league: Int?):
            LiveData<MutableList<FootballTeam>> {
        job = Job()
        return object : LiveData<MutableList<FootballTeam>>() {
            override fun onActive() {
                super.onActive()
                job?.let { thisJob ->
                    CoroutineScope(Dispatchers.IO + thisJob).launch {
                        val leagueName = LeagueHelper.getLeagueName()
                        val teams = HttpHelper.get("$BASE_URL$country/api/league_list/$leagueName/")

                        withContext(Dispatchers.Main) {
                            val teamsList = fromJson<List<Team>>(teams)
                            var footballTeams: MutableList<FootballTeam> = mutableListOf()
                            teamsList.forEach {team ->
                                val footballTeam = FootballTeam()
                                footballTeam.intlName = team.intlName
                                footballTeam.teamName = team.teamName
                                footballTeam.league = FoppalApplication.getInstance().league?.leagueName
                                saveFootballTeams(footballTeam)
                                footballTeams.add(footballTeam)
                            }
                            value = footballTeams
                            thisJob.complete()
                        }
                    }
                }
            }
        }
    }

    fun saveFootballTeams(team: FootballTeam):
            LiveData<Boolean> {
        job = Job()
        return object : LiveData<Boolean>() {
            override fun onActive() {
                super.onActive()
                job?.let { thisJob ->
                    CoroutineScope(Dispatchers.IO + thisJob).launch {
                        dao.insert(team)
                        withContext(Dispatchers.Main) {
                            value = true
                            thisJob.complete()
                        }
                    }
                }
            }
        }
    }

    fun cancelJobs () {
        job?.cancel()
    }
}