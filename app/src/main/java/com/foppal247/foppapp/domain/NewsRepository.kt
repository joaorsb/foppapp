package com.foppal247.foppapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.foppal247.foppapp.R
import com.foppal247.foppapp.domain.model.News
import com.foppal247.foppapp.extensions.fromJson
import com.foppal247.foppapp.utils.HttpHelper
import com.foppal247.foppapp.utils.LeagueHelper
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object NewsRepository {
    val BASE_URL = "https://foppal247.com/"
    var job: CompletableJob? = null

    fun getApiNews(country: String?, page: Int?,
                   league: Int?, team: String?, englishNews: Boolean? = false):
            LiveData<MutableList<News>> {
        job = Job()
        return object : LiveData<MutableList<News>>() {
            override fun onActive() {
                super.onActive()
                job?.let { thisJob ->
                    CoroutineScope(IO + thisJob).launch {
                        val url = getUrl(country, page, league, team, englishNews as Boolean)
                        val news = HttpHelper.get(url)
                        withContext(Main) {
                            value = fromJson(news)
                            thisJob.complete()
                        }
                    }
                }
            }
        }
    }

    fun getUrl(country: String?, page: Int?, league: Int?,
               team: String?, englishNews: Boolean): String {

        if(englishNews){
            return "$BASE_URL$country/api/language/en?page=$page"
        }

        if(!team.isNullOrEmpty()){
            return "$BASE_URL$country/api/teams/$team?page=$page"
        }

        if(league == R.string.all) {
            return "$BASE_URL$country/api/?page=$page"
        }

        val leagueName = LeagueHelper.getLeagueName()
        return "$BASE_URL$country/api/leagues/$leagueName?page=$page"

    }

    fun cancelJobs () {
        job?.cancel()
    }
}