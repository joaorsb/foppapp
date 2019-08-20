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

    @Query("SELECT * from football_team where league = :leagueId")
    fun getFootballTeamsByLeague(leagueId: Int?): MutableList<FootballTeam>

    @Query("Select * from football_team")
    fun findAll(): MutableList<FootballTeam>

    @Insert
    fun insert(footballTeam: FootballTeam)

    @Delete
    fun delete(footballTeam: FootballTeam)
}