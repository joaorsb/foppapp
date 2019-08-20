package com.foppal247.foppapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.foppal247.foppapp.R
import kotlinx.android.synthetic.main.fragment_teams.*
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.activity.NewsListActivity
import com.foppal247.foppapp.adapter.TeamsAdapter
import com.foppal247.foppapp.domain.FootballTeamsService
import com.foppal247.foppapp.domain.dao.DatabaseManager
import com.foppal247.foppapp.domain.model.FootballTeam
import com.foppal247.foppapp.domain.model.Team
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.uiThread


class TeamsFragment : BaseFragment() {
    private var teamsList = listOf<Team>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              icicle: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_teams, container, false)
        return view

    }

    override fun onViewCreated(view: View, icicle: Bundle?) {
        super.onViewCreated(view, icicle)
        super.checkConnection()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)

    }

    @SuppressLint("ResourceAsColor")
    override fun onResume() {
        super.onResume()
        swipeFragment.setOnRefreshListener { taskGetTeams() }
        swipeFragment.setColorSchemeColors(
            R.color.refresh_progress_1,
            R.color.refresh_progress_2,
            R.color.refresh_progress_3
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        taskGetLocalTeams()

        if(super.hasConnection){
            if(FoppalApplication.getInstance().footballTeams.isNullOrEmpty()) {
                taskGetTeams()
            } else {
                recyclerView.adapter = TeamsAdapter(FoppalApplication.getInstance().footballTeams) { onClickTeam(it)}
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        teamsList = listOf()
    }

    private fun taskGetTeams(){
        doAsync {
            swipeFragment.isRefreshing = ! swipeFragment.isRefreshing
                teamsList = FootballTeamsService.getFootballTeamsByLeagueREST()
                teamsList.forEach {team: Team -> run {
                        val footballTeam = FootballTeam()
                        footballTeam.intlName = team.intlName
                        footballTeam.teamName = team.teamName
                        footballTeam.league = FoppalApplication.getInstance().league?.leagueName
                        val dao = DatabaseManager.getFootballTeamsDAO()
                        dao.insert(footballTeam)
                        FoppalApplication.getInstance().footballTeams.add(footballTeam)
                    }
                }
            uiThread {
                recyclerView.adapter = TeamsAdapter(FoppalApplication.getInstance().footballTeams) { onClickTeam(it)}
                swipeFragment.isRefreshing = false
            }
        }

    }

    fun onClickTeam(team: FootballTeam) {
        FoppalApplication.getInstance().englishNews = false
        FoppalApplication.getInstance().newsList = listOf()

        FoppalApplication.getInstance().selectedIntlTeamName = team.intlName
        FoppalApplication.getInstance().selectedTeamName = team.teamName
        startActivity<NewsListActivity>("leagueType" to FoppalApplication.getInstance().league)
    }


}