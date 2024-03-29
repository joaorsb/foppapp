package com.foppal247.foppapp.domain

import androidx.lifecycle.LiveData
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
                        val teams = dao.findAll()
                        withContext(Main){
                            value = teams
                            job?.complete()
                        }
                    }
                }
            }
        }
    }

    fun saveFavoriteTeam(teamName: String, country: String) {
        job = Job()
        job?.let {thisJob ->
            CoroutineScope(IO + thisJob).launch {
                val team = daoTeam.getByIntlName(teamName)
                val favorite = FavoriteTeam()
                if(team != null){
                    favorite.country = country
                    favorite.intlName = team.intlName
                    favorite.league = team.league
                    favorite.teamName = team.teamName
                    dao.insert(favorite)
                }
                FirebaseMessaging.getInstance().subscribeToTopic(favorite.intlName)
                    .addOnCompleteListener {
                    }
                withContext(Main){
                    EventBus.getDefault().post(FavoriteEvent(favorite))
                    job?.complete()
                }
            }
        }
    }

    fun deleteFavoriteTeam(favoriteTeam: FavoriteTeam) {
        job = Job()
        job?.let {thisJob ->
            CoroutineScope(IO + thisJob).launch {
                dao.delete(favoriteTeam)
                EventBus.getDefault().post(FavoriteEvent(favoriteTeam))
                FirebaseMessaging.getInstance().unsubscribeFromTopic(favoriteTeam.intlName)
                    .addOnCompleteListener { task ->
                    }
                withContext(Main){
                    job?.complete()
                }
            }
        }
    }

    fun cancel(){
        job?.cancel()
    }

}