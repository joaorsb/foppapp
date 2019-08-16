package com.foppal247.foppapp.domain

import android.content.Context
import android.util.Log
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.R
import com.foppal247.foppapp.extensions.fromJson
import java.net.URL

object NewsService {
    private val TAG = "NewsService"
    fun getAllNews(context: Context?) : List<News>{
        var country = getCountry(context)
        val mainURL = "https://www.foppal247.com/$country/api/?page=1"
        Log.d(TAG, mainURL)
        var json = URL(mainURL).readText()
        var news = fromJson<List<News>>(json)
        Log.d(TAG, "${news.size} links found")

        return news
    }

    fun getLeagueNews(context: Context?) : List<News>{
        var leagueName = "eliteserien"
        var country = getCountry(context)

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
                leagueName = "laliga2"
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
        }
        val leagueUrl = "https://www.foppal247.com/${country}/api/leagues/$leagueName?page=1"
        Log.d(TAG, leagueUrl)
        var json = URL(leagueUrl).readText()
        var news = fromJson<List<News>>(json)
        Log.d(TAG, "${news.size} links found")

        return news
    }

    fun getCountry (context: Context?): String {
        var country = "norge"
        when(FoppalApplication.getInstance().country) {
            context?.getString(R.string.norge) -> { country = "norge" }
            context?.getString(R.string.brasil) -> { country = "brazil" }
            context?.getString(R.string.espana) -> { country = "espana" }
            context?.getString(R.string.sverige) -> { country = "sverige" }
            context?.getString(R.string.nederland) -> { country = "nederland" }
            context?.getString(R.string.deutschland) -> { country = "deutschland" }
        }
        return country
    }
}