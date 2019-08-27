package com.foppal247.foppapp.domain.dao

import androidx.room.Room
import com.foppal247.foppapp.FoppalApplication

object FootballTeamsDatabaseManager {
    private var dbInstance: FootballTeamsDatabase
    init {
        val appContext = FoppalApplication.getInstance().applicationContext
        dbInstance = Room.databaseBuilder(
            appContext,
            FootballTeamsDatabase::class.java,
            "football_teams.sqlite"
        ).build()
    }

    fun getFootballTeamsDAO(): FootballTeamDAO {
        return dbInstance.footballTeamDAO()
    }

}