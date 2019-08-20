package com.foppal247.foppapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "football_team")
class FootballTeam {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var teamName = ""
    var intlName = ""
    var league: Int? = 0

    override fun toString(): String {
        return intlName
    }
}