package com.foppal247.foppapp.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.domain.model.LeagueTypes
import com.foppal247.foppapp.R
import com.foppal247.foppapp.domain.FavoriteTeamsService
import com.foppal247.foppapp.extensions.addFragment
import com.foppal247.foppapp.extensions.setupToolbar
import com.foppal247.foppapp.extensions.toast
import com.foppal247.foppapp.fragments.NewsFragment


class NewsListActivity : BaseActivity() {

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_news_list)
        lateinit var league: LeagueTypes

        if(intent.getSerializableExtra("leagueType") != null){
            league = intent.getSerializableExtra("leagueType") as LeagueTypes
        }
        val title: String

        if(FoppalApplication.getInstance().selectedIntlTeamName.isNullOrBlank()){
            if(league != LeagueTypes.serie_b){
                title = getString(league.leagueName)
            } else {
                title = getString(R.string.serieb)
            }
        } else {
            title = FoppalApplication.getInstance().selectedTeamName
        }

        setupToolbar(R.id.toolbar, title, true)
        if(icicle == null){
            addFragment(R.id.container, NewsFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var isFavorite = false

        if( ! foppalInstance.favoriteTeams.isNullOrEmpty()) {
            foppalInstance.favoriteTeams.forEach {
                if(it.intlName == foppalInstance.selectedIntlTeamName){
                    isFavorite = true
                }
            }
        }

        if( ! isFavorite && foppalInstance.selectedIntlTeamName != "") {
            menuInflater.inflate(R.menu.menu_favorites, menu)

        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.favorite) {
            FavoriteTeamsService.saveFavoriteTeam()
            toast(getText(R.string.favorite_added))
        }
        return super.onOptionsItemSelected(item)
    }
}
