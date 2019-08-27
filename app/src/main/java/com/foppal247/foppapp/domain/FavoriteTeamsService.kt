package com.foppal247.foppapp.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.domain.dao.FavoriteTeamsDatabaseManager
import com.foppal247.foppapp.domain.dao.FootballTeamsDatabaseManager
import com.foppal247.foppapp.domain.model.FavoriteTeam
import com.foppal247.foppapp.extensions.FavoriteEvent
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

object FavoriteTeamsService {
    private val dao = FavoriteTeamsDatabaseManager.getFavoriteTeamsDAO()

    fun getFavoriteTeams(): List<FavoriteTeam> {
        return dao.findAll()
    }

    fun saveFavoriteTeam() {
        val daoFavoriteTeam = FavoriteTeamsDatabaseManager.getFavoriteTeamsDAO()
        val daoTeam = FootballTeamsDatabaseManager.getFootballTeamsDAO()
        val team = daoTeam.getByIntlName(FoppalApplication.getInstance().selectedIntlTeamName as String)
        val favorite = FavoriteTeam()
        favorite.country = FoppalApplication.getInstance().country as String
        favorite.intlName = team?.intlName as String
        favorite.league = team.league
        favorite.teamName = team.teamName
        daoFavoriteTeam.insert(favorite)
        doAsync {
            uiThread {
                EventBus.getDefault().post(FavoriteEvent(favorite))
            }
        }

    }

}