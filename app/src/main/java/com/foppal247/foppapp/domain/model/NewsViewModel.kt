package com.foppal247.foppapp.domain.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class NewsViewModel : ViewModel() {
    private var newsLiveData = MutableLiveData<News>()
    var selectedTeam = MutableLiveData<String>()

    val news: LiveData<News>
        get() = newsLiveData

    init  {

    }

    internal fun loadNewsREST() {

    }
}// trigger user load.
