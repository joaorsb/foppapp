package com.foppal247.foppapp.domain

import androidx.lifecycle.LiveData
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.domain.dao.FavoriteTeamsDatabaseManager
import com.foppal247.foppapp.domain.dao.FootballTeamsDatabaseManager
import com.foppal247.foppapp.domain.model.FavoriteTeam
import com.foppal247.foppapp.extensions.FavoriteEvent
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import org.greenrobot.eventbus.EventBus

object FavoriteTeamsRepository {
    var job: CompletableJob? = null
    private val dao = FavoriteTeamsDatabaseManager.getFavoriteTeamsDAO()
    private val daoTeam = FootballTeamsDatabaseManager.getFootballTeamsDAO()


    fun getFavoriteTeams(): LiveData<MutableList<FavoriteTeam>> {
        job = Job()
        return object : LiveData<MutableList<FavoriteTeam>>() {
            override fun onActive() {
                super.onActive()
                job?.let {thisJob ->
                    CoroutineScope(IO + thisJob).launch {
                        withContext(Main){
                            value = dao.findAll()
                            job?.complete()
                        }
                    }
                }
            }
        }
    }

    fun setFavoriteTeam(): LiveData<Boolean> {
        job = Job()
        return object : LiveData<Boolean>() {
            override fun onActive() {
                super.onActive()
                job?.let {thisJob ->
                    CoroutineScope(IO + thisJob).launch {
                        withContext(Main){
                            val team = daoTeam.getByIntlName(FoppalApplication.getInstance().selectedIntlTeamName)
                            val favorite = FavoriteTeam()
                            var result = false
                            if(team != null){
                                favorite.country = FoppalApplication.getInstance().country
                                favorite.intlName = team?.intlName as String
                                favorite.league = team.league
                                favorite.teamName = team.teamName
                                dao.insert(favorite)
                                EventBus.getDefault().post(FavoriteEvent(favorite))
                                FirebaseMessaging.getInstance().subscribeToTopic(favorite.intlName)
                                    .addOnCompleteListener {
                                        result = true
                                    }
                            }
                            value = result
                            job?.complete()
                        }
                    }
                }
            }
        }
    }

    fun deleteFavoriteTeam(favoriteTeam: FavoriteTeam): LiveData<Boolean> {
        job = Job()
        return object : LiveData<Boolean>() {
            override fun onActive() {
                super.onActive()
                job?.let {thisJob ->
                    CoroutineScope(IO + thisJob).launch {
                        withContext(Main){
                            var result = false
                            dao.delete(favoriteTeam)
                            EventBus.getDefault().post(FavoriteEvent(favoriteTeam))
                            FirebaseMessaging.getInstance().unsubscribeFromTopic(favoriteTeam.intlName)
                                .addOnCompleteListener { task ->
                                    result = true
                                }
                            value = result
                            job?.complete()
                        }
                    }
                }
            }
        }
    }

    fun cancel(){
        job?.cancel()
    }

}