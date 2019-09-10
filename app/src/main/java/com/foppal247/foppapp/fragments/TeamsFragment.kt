package com.foppal247.foppapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.foppal247.foppapp.R
import kotlinx.android.synthetic.main.fragment_teams.*
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.activity.NewsListActivity
import com.foppal247.foppapp.adapter.TeamsAdapter
import com.foppal247.foppapp.domain.model.FootballTeam
import com.foppal247.foppapp.domain.model.Team
import com.foppal247.foppapp.viewModels.FootballTeamsViewModel
import org.jetbrains.anko.support.v4.startActivity


class TeamsFragment : BaseFragment() {
    private var teamsList = listOf<Team>()
    private lateinit var teamsViewModel: FootballTeamsViewModel
    private val adapter = TeamsAdapter(FoppalApplication.getInstance().footballTeams) { onClickTeam(it)}

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
        recyclerView.adapter = adapter
        teamsViewModel = ViewModelProvider(this).get(FootballTeamsViewModel::class.java)
        teamsViewModel.apiTeams.observe(this, Observer {teams ->
            adapter.setTeamsData(teams)
            swipeFragment.isRefreshing = false

        })
        teamsViewModel.localTeams.observe(this, Observer { teams ->
            if(teams.isNotEmpty()) {
                adapter.setTeamsData(teams)
                swipeFragment.isRefreshing = false

            } else {
                teamsViewModel.setHasLocalTeams(false)
                swipeFragment.isRefreshing = false

            }
        })

    }

    @SuppressLint("ResourceAsColor")
    override fun onResume() {
        super.onResume()
        swipeFragment.setOnRefreshListener {
            teamsViewModel.setCountry(FoppalApplication.getInstance().country)
            teamsViewModel.setLeague(FoppalApplication.getInstance().league?.leagueName as Int)
            swipeFragment.isRefreshing = false

        }
        swipeFragment.setColorSchemeColors(
            R.color.refresh_progress_1,
            R.color.refresh_progress_2,
            R.color.refresh_progress_3
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(super.hasConnection){
            swipeFragment.isRefreshing = true
            teamsViewModel.setCountry(FoppalApplication.getInstance().country)
            teamsViewModel.setLeague(FoppalApplication.getInstance().league?.leagueName as Int)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        teamsList = listOf()
        teamsViewModel.cancel()
    }

    private fun onClickTeam(team: FootballTeam) {
        FoppalApplication.getInstance().englishNews = false
        FoppalApplication.getInstance().newsList = mutableListOf()

        FoppalApplication.getInstance().selectedIntlTeamName = team.intlName
        FoppalApplication.getInstance().selectedTeamName = team.teamName
        startActivity<NewsListActivity>("leagueType" to FoppalApplication.getInstance().league)
    }
}