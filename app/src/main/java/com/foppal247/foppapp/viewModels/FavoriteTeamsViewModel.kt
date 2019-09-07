package com.foppal247.foppapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.foppal247.foppapp.domain.FavoriteTeamsRepository
import com.foppal247.foppapp.domain.model.FavoriteTeam

class FavoriteTeamsViewModel : ViewModel() {
    val loadFavorites: MutableLiveData<Boolean> = MutableLiveData()
    val favoriteTeams: LiveData<MutableList<FavoriteTeam>> = Transformations
        .switchMap(loadFavorites) {
            FavoriteTeamsRepository.getFavoriteTeams()
        }

    fun setLoadFavorites(value: Boolean){
        this.loadFavorites.value = value
    }

    fun cancelJobs(){
        FavoriteTeamsRepository.cancel()
    }

}