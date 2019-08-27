package com.foppal247.foppapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite_teams")
class FavoriteTeam{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var teamName = ""
    var intlName = ""
    var league: Int? = 0
    var country = ""

    override fun toString(): String {
        return intlName
    }
}