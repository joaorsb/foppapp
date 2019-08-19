package com.foppal247.foppapp.domain.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.foppal247.foppapp.domain.model.FootballTeam

@Database(entities = arrayOf(FootballTeam::class), version = 1)
abstract class FootballTeamsDatabase : RoomDatabase() {
    abstract fun footballTeamDAO(): FootballTeamDAO
}