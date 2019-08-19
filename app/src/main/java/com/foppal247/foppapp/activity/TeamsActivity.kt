package com.foppal247.foppapp.activity

import android.os.Bundle
import com.foppal247.foppapp.R
import com.foppal247.foppapp.domain.model.LeagueTypes
import com.foppal247.foppapp.extensions.addFragment
import com.foppal247.foppapp.extensions.setupToolbar
import com.foppal247.foppapp.fragments.TeamsFragment

class TeamsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)
        val league = intent.getSerializableExtra("leagueType") as LeagueTypes
        val title: String

        if(league != LeagueTypes.serie_b){
            title = getString(league.leagueName)
        } else {
            title = getString(R.string.serieb)
        }
        setupToolbar(R.id.toolbar, title, true)

        if(savedInstanceState == null){
            addFragment(R.id.container, TeamsFragment())
        }
    }
}
