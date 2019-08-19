package com.foppal247.foppapp.utils

import android.content.Context
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.domain.FootballTeamsService
import com.foppal247.foppapp.domain.model.Team
import org.jetbrains.anko.doAsync

object FootballTeamsHelper {
    fun taskFootballTeamsRest(context: Context?){
        var teams: List<Team>
        if(AndroidUtils.isNetworkAvailable(context)){
            doAsync {
                teams = FootballTeamsService.getFootballTeamsByLeagueREST()
            }
        }
    }

    fun getFootballTeamsLocal(){
        doAsync {
            FoppalApplication.getInstance().footballTeams = FootballTeamsService.getFootballTeams()
        }
    }
}