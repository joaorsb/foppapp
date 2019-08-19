package com.foppal247.foppapp.utils

import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.domain.model.LeagueTypes

object LeagueHelper {
    fun getLeagueName(): String {
        var leagueName = "eliteserien"
        when(FoppalApplication.getInstance().league){
            LeagueTypes.eliteserien -> {
                leagueName = "eliteserien"
            }
            LeagueTypes.obos -> {
                leagueName = "obos"
            }
            LeagueTypes.postnord -> {
                leagueName = "postnord"
            }
            LeagueTypes.brasileirao -> {
                leagueName = "brasileirao"
            }
            LeagueTypes.serie_b -> {
                leagueName = "serie_b"
            }
            LeagueTypes.laliga -> {
                leagueName = "laliga"
            }
            LeagueTypes.laliga2 -> {
                leagueName = "la_liga2"
            }
            LeagueTypes.allsvenskan -> {
                leagueName = "allsvenskan"
            }
            LeagueTypes.eredivisie -> {
                leagueName = "eredivisie"
            }
            LeagueTypes.bundesliga -> {
                leagueName = "bundesliga"
            }
            LeagueTypes.bundesliga2 -> {
                leagueName = "bundesliga2"
            }
            else -> { leagueName = "eliteserien"}
        }
        return leagueName
    }
}