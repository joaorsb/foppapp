package com.foppal247.foppapp.domain.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.foppal247.foppapp.domain.model.FavoriteTeam

@Database(entities = arrayOf(FavoriteTeam::class), version = 1)
abstract class FavoriteTeamsDatabase : RoomDatabase() {
    abstract fun favoriteTeamsDAO(): FavoriteTeamsDAO
}