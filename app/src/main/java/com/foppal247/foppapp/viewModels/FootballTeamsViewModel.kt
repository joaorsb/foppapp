package com.foppal247.foppapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.foppal247.foppapp.domain.FootballTeamsRepository
import com.foppal247.foppapp.domain.model.FootballTeam

class FootballTeamsViewModel: ViewModel(){
    val country: MutableLiveData<String> = MutableLiveData()
    val league: MutableLiveData<Int> = MutableLiveData()
    val hasLocalTeams: MutableLiveData<Boolean> = MutableLiveData()

    val apiTeams: LiveData<MutableList<FootballTeam>> = Transformations
        .switchMap(hasLocalTeams) {
            FootballTeamsRepository.getApiFootballTeams(country.value, league.value)
        }

    val localTeams: LiveData<MutableList<FootballTeam>> = Transformations
        .switchMap(league) {
            FootballTeamsRepository.getLocalFootballTeamsByLeague(league.value)
        }

    fun setCountry(country: String) {
        val update = country
        if(this.country.value == update) {
            return
        }
        this.country.value = update
    }

    fun setLeague(league: Int) {
        val update = league
        if(this.league.value == update) {
            return
        }
        this.league.value = update
    }

    fun setHasLocalTeams(hasLocalTeams: Boolean) {
        val update = hasLocalTeams
        if(this.hasLocalTeams.value == update) {
            return
        }
        this.hasLocalTeams.value = hasLocalTeams
    }

    fun cancel(){
        FootballTeamsRepository.cancelJobs()
    }
}