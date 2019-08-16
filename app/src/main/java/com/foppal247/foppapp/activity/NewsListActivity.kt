package com.foppal247.foppapp.activity

import android.os.Bundle
import com.foppal247.foppapp.domain.LeagueTypes
import com.foppal247.foppapp.R
import com.foppal247.foppapp.extensions.addFragment
import com.foppal247.foppapp.extensions.setupToolbar
import com.foppal247.foppapp.fragments.LeaguesFragment

class NewsListActivity : BaseActivity() {

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_news_list)
        val league = intent.getSerializableExtra("leagueType") as LeagueTypes
        val title = getString(league.string)
        setupToolbar(R.id.toolbar, title, true)
        if(icicle == null){
            addFragment(R.id.container, LeaguesFragment())
        }

    }
}
