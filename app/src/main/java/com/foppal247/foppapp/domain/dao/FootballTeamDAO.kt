package com.foppal247.foppapp.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.foppal247.foppapp.domain.model.FootballTeam

@Dao
interface FootballTeamDAO {
    @Query("SELECT * from football_team where id = :id")
    fun getById(id: Long): FootballTeam?

    @Query("SELECT * from football_team where intlName = :intlName")
    fun getByIntlName(intlName: String): FootballTeam?

    @Query("Select * from football_team")
    fun findAll(): List<FootballTeam>

    @Insert
    fun insert(footballTeam: FootballTeam)

    @Delete
    fun delete(footballTeam: FootballTeam)
}