package com.foppal247.foppapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.foppal247.foppapp.domain.NewsRepository
import com.foppal247.foppapp.domain.model.News

class NewsViewModel: ViewModel() {
    val page: MutableLiveData<Int> = MutableLiveData()
    val country: MutableLiveData<String> = MutableLiveData()
    val league: MutableLiveData<Int> = MutableLiveData()
    val team: MutableLiveData<String> = MutableLiveData()
    val englishNews: MutableLiveData<Boolean> = MutableLiveData()

    val newsList: LiveData<MutableList<News>> = Transformations
        .switchMap(page) {
            NewsRepository.getApiNews(country.value, page.value, league.value, team.value, englishNews.value)
        }

    fun setPage(page: Int){
        val update = page
        if (this.page.value == update) {
            return
        }
        this.page.value = update
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

    fun setEnglishNews(englishNews: Boolean) {
        val update = englishNews
        if(this.englishNews.value == englishNews) {
            return
        }
        this.englishNews.value = update
    }

    fun setTeam(team: String) {
        val update = team
        if(this.team.value == update) {
            return
        }
        this.team.value = update
    }

    fun cancelJobs(){
        NewsRepository.cancelJobs()
    }
}