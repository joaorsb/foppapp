package com.foppal247.foppapp.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.drawerlayout.widget.DrawerLayout
import com.foppal247.foppapp.R
import com.foppal247.foppapp.domain.FootballTeamsService
import com.foppal247.foppapp.extensions.setupToolbar
import com.google.android.material.navigation.NavigationView
import com.foppal247.foppapp.domain.model.LeagueTypes
import com.foppal247.foppapp.extensions.addFragment
import com.foppal247.foppapp.fragments.CountriesFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.doAsync
import com.foppal247.foppapp.fragments.FavoritesFragment


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_main)
        setupToolbar(R.id.toolbar)
        setupNavDrawer()
        if(icicle == null){
            addFragment(R.id.countriesContainer, CountriesFragment())
            addFragment(R.id.favoritesContainer, FavoritesFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        nav_view.menu.forEach { item: MenuItem ->  item.isVisible = false}
        nav_view.menu.forEach { item: MenuItem ->
            if (item.groupId == foppalInstance.menuGroupId) {
                nav_view.menu.setGroupVisible(foppalInstance.menuGroupId, true)
            }
        }
    }

    private fun setupNavDrawer() {
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        foppalInstance.newsList = mutableListOf()
        foppalInstance.englishNews = false
        when(item.itemId) {
            R.id.nav_item_norge_all, R.id.nav_item_brasil_all,
            R.id.nav_item_deutschland_all, R.id.nav_item_espana_all,
            R.id.nav_item_nederland_all, R.id.nav_item_sverige_all -> {
                foppalInstance.league = LeagueTypes.all
                startActivity<NewsListActivity>("leagueType" to LeagueTypes.all)
            }
            R.id.nav_item_deutschland_all_en, R.id.nav_item_espana_all_en,
            R.id.nav_item_nederland_all_en -> {
                foppalInstance.league = LeagueTypes.all
                foppalInstance.englishNews = true
                startActivity<NewsListActivity>("leagueType" to LeagueTypes.all)
            }
            R.id.nav_item_eliteserien -> {
                foppalInstance.league = LeagueTypes.eliteserien
                startActivity<NewsListActivity>("leagueType" to LeagueTypes.eliteserien)
            }
            R.id.nav_item_eliteserien_teams -> {
                foppalInstance.league = LeagueTypes.eliteserien
                taskGetLocalTeams()
                startActivity<TeamsActivity>("leagueType" to LeagueTypes.eliteserien)
            }
            R.id.nav_item_obos -> {
                foppalInstance.league = LeagueTypes.obos
                startActivity<NewsListActivity>("leagueType" to LeagueTypes.obos)
            }
            R.id.nav_item_obos_teams -> {
                foppalInstance.league = LeagueTypes.obos
                taskGetLocalTeams()
                startActivity<TeamsActivity>("leagueType" to LeagueTypes.obos)
            }
            R.id.nav_item_postnord -> {
                foppalInstance.league = LeagueTypes.postnord
                startActivity<NewsListActivity>("leagueType" to LeagueTypes.postnord)
            }
            R.id.nav_item_postnord_teams -> {
                foppalInstance.league = LeagueTypes.postnord
                taskGetLocalTeams()
                startActivity<TeamsActivity>("leagueType" to LeagueTypes.postnord)
            }
            R.id.nav_item_brasileirao -> {
                foppalInstance.league = LeagueTypes.brasileirao
                startActivity<NewsListActivity>("leagueType" to LeagueTypes.brasileirao)
            }
            R.id.nav_item_brasileirao_teams -> {
                foppalInstance.league = LeagueTypes.brasileirao
                taskGetLocalTeams()
                startActivity<TeamsActivity>("leagueType" to LeagueTypes.brasileirao)
            }
            R.id.nav_item_serieb -> {
                foppalInstance.league = LeagueTypes.serie_b
                startActivity<NewsListActivity>("leagueType" to LeagueTypes.serie_b)
            }
            R.id.nav_item_serieb_teams -> {
                foppalInstance.league = LeagueTypes.serie_b
                taskGetLocalTeams()
                startActivity<TeamsActivity>("leagueType" to LeagueTypes.serie_b)
            }
            R.id.nav_item_laliga -> {
                foppalInstance.league = LeagueTypes.laliga
                startActivity<NewsListActivity>("leagueType" to LeagueTypes.laliga)
            }
            R.id.nav_item_laliga_teams -> {
                foppalInstance.league = LeagueTypes.laliga
                taskGetLocalTeams()
                startActivity<TeamsActivity>("leagueType" to LeagueTypes.laliga)
            }
            R.id.nav_item_laliga2 -> {
                foppalInstance.league = LeagueTypes.laliga2
                startActivity<NewsListActivity>("leagueType" to LeagueTypes.laliga2)
            }
            R.id.nav_item_laliga2_teams -> {
                foppalInstance.league = LeagueTypes.laliga2
                taskGetLocalTeams()
                startActivity<TeamsActivity>("leagueType" to LeagueTypes.laliga2)
            }
            R.id.nav_item_allsvenskan -> {
                foppalInstance.league = LeagueTypes.allsvenskan
                startActivity<NewsListActivity>("leagueType" to LeagueTypes.allsvenskan)
            }
            R.id.nav_item_allsvenskan_teams -> {
                foppalInstance.league = LeagueTypes.allsvenskan
                taskGetLocalTeams()
                startActivity<TeamsActivity>("leagueType" to LeagueTypes.allsvenskan)
            }
            R.id.nav_item_eredivisie -> {
                foppalInstance.league = LeagueTypes.eredivisie
                startActivity<NewsListActivity>("leagueType" to LeagueTypes.eredivisie)
            }
            R.id.nav_item_eredivisie_teams -> {
                foppalInstance.league = LeagueTypes.eredivisie
                taskGetLocalTeams()
                startActivity<TeamsActivity>("leagueType" to LeagueTypes.eredivisie)
            }
            R.id.nav_item_bundesliga -> {
                foppalInstance.league = LeagueTypes.bundesliga
                startActivity<NewsListActivity>("leagueType" to LeagueTypes.bundesliga)
            }
            R.id.nav_item_bundesliga_teams -> {
                foppalInstance.league = LeagueTypes.bundesliga
                taskGetLocalTeams()
                startActivity<TeamsActivity>("leagueType" to LeagueTypes.bundesliga)
            }
            R.id.nav_item_bundesliga2 -> {
                foppalInstance.league = LeagueTypes.bundesliga2
                startActivity<NewsListActivity>("leagueType" to LeagueTypes.bundesliga2)
            }
            R.id.nav_item_bundesliga2_teams -> {
                foppalInstance.league = LeagueTypes.bundesliga2
                taskGetLocalTeams()
                startActivity<TeamsActivity>("leagueType" to LeagueTypes.bundesliga2)
            }
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun taskGetLocalTeams() {
        doAsync {
            foppalInstance.footballTeams =
                FootballTeamsService.getFootballTeamsByLeague(foppalInstance.league?.leagueName)
        }
    }
}
