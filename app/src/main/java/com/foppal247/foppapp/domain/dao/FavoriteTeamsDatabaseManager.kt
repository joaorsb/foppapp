package com.foppal247.foppapp.domain.dao

import androidx.room.Room
import com.foppal247.foppapp.FoppalApplication

object FavoriteTeamsDatabaseManager {
    private var dbInstance: FavoriteTeamsDatabase
    init {
        val appContext = FoppalApplication.getInstance().applicationContext
        dbInstance = Room.databaseBuilder(
            appContext,
            FavoriteTeamsDatabase::class.java,
            "favorite_teams.sqlite"
        ).build()
    }

    fun getFavoriteTeamsDAO(): FavoriteTeamsDAO {
        return dbInstance.favoriteTeamsDAO()
    }

}