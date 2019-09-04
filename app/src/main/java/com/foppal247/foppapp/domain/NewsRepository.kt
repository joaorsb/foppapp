package com.foppal247.foppapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.foppal247.foppapp.domain.model.News
import com.foppal247.foppapp.extensions.fromJson
import com.foppal247.foppapp.utils.HttpHelper
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object NewsRepository {
    val BASE_URL = "https://foppal247.com/"
    var job: CompletableJob? = null

    fun getAllNews(country: String, page: Int):
            LiveData<List<News>> {
        job = Job()
        return object : LiveData<List<News>>() {
            override fun onActive() {
                super.onActive()
                job?.let { thisJob ->
                    CoroutineScope(IO + thisJob).launch {
                        val news = HttpHelper.get("$BASE_URL${country}/api/?page=${page}")
                        withContext(Main) {
                            value = fromJson(news)
                            thisJob.complete()
                        }

                    }
                }
            }
        }
    }

    fun cancelJobs () {
        job?.cancel()
    }
}