package com.foppal247.foppapp.domain.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class NewsViewModel : ViewModel() {
    private val newsLiveData = MutableLiveData<News>()
    var selectedTeam = MutableLiveData<String>()

    val news: LiveData<News>
        get() = newsLiveData

    internal fun doAction() {
        // depending on the action, do necessary business logic calls and update the
        // userLiveData.
    }
}// trigger user load.
