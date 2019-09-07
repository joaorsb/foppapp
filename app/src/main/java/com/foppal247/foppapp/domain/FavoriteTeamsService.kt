package com.foppal247.foppapp.domain

import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.domain.dao.FavoriteTeamsDatabaseManager
import com.foppal247.foppapp.domain.dao.FootballTeamsDatabaseManager
import com.foppal247.foppapp.domain.model.FavoriteTeam
import com.foppal247.foppapp.extensions.FavoriteEvent
import com.google.firebase.messaging.FirebaseMessaging
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

object FavoriteTeamsService {
    private val dao = FavoriteTeamsDatabaseManager.getFavoriteTeamsDAO()

    fun saveFavoriteTeam() {
        doAsync {
            val daoFavoriteTeam = FavoriteTeamsDatabaseManager.getFavoriteTeamsDAO()
            val daoTeam = FootballTeamsDatabaseManager.getFootballTeamsDAO()
            val team = daoTeam.getByIntlName(FoppalApplication.getInstance().selectedIntlTeamName)
            val favorite = FavoriteTeam()
            favorite.country = FoppalApplication.getInstance().country
            favorite.intlName = team?.intlName as String
            favorite.league = team.league
            favorite.teamName = team.teamName
            daoFavoriteTeam.insert(favorite)
            uiThread {
                EventBus.getDefault().post(FavoriteEvent(favorite))
                FirebaseMessaging.getInstance().subscribeToTopic(favorite.intlName)
                    .addOnCompleteListener { task ->
                    }
            }
        }
    }

    fun deleteFavorite(favoriteTeam: FavoriteTeam){
        doAsync {
            dao.delete(favoriteTeam)
            uiThread {
                EventBus.getDefault().post(FavoriteEvent(favoriteTeam))
                FirebaseMessaging.getInstance().unsubscribeFromTopic(favoriteTeam.intlName)
                    .addOnCompleteListener { task ->
                    }
            }
        }
    }

}