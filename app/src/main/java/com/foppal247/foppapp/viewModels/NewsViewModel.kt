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

//    val newsList: LiveData<List<News>> = Transformations
//        .switchMap(page) {
////            NewsRepository.getAllNews(country, page)
//        }

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

    fun cancelJobs(){
        NewsRepository.cancelJobs()
    }
}