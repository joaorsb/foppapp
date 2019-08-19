package com.foppal247.foppapp.activity

import android.os.Bundle
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.domain.model.LeagueTypes
import com.foppal247.foppapp.R
import com.foppal247.foppapp.extensions.addFragment
import com.foppal247.foppapp.extensions.setupToolbar
import com.foppal247.foppapp.fragments.NewsFragment

class NewsListActivity : BaseActivity() {

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_news_list)
        val league = intent.getSerializableExtra("leagueType") as LeagueTypes
        val title: String


        if(FoppalApplication.getInstance().selectedIntlTeamName.isNullOrBlank()){
            if(league != LeagueTypes.serie_b){
                title = getString(league.leagueName)
            } else {
                title = getString(R.string.serieb)
            }
        } else {
            title = FoppalApplication.getInstance().selectedTeamName ?: getString(league.leagueName)
        }

        setupToolbar(R.id.toolbar, title, true)
        if(icicle == null){
            addFragment(R.id.container, NewsFragment())
        }
    }
}
