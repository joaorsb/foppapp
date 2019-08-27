package com.foppal247.foppapp.domain.dao

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.foppal247.foppapp.domain.model.FavoriteTeam

@Dao
interface FavoriteTeamsDAO {
    @Query("SELECT * from favorite_teams where id = :id")
    fun getById(id: Long): FavoriteTeam?

    @Query("SELECT * from favorite_teams where intlName = :intlName")
    fun getByIntlName(intlName: String): FavoriteTeam?

    @Query("SELECT * from favorite_teams where league = :leagueId")
    fun getFootballTeamsByLeague(leagueId: Int?): List<FavoriteTeam>

    @Query("Select * from favorite_teams")
    fun findAll(): List<FavoriteTeam>

    @Insert
    fun insert(favoriteTeam: FavoriteTeam)

    @Delete
    fun delete(favoriteTeam: FavoriteTeam)
}