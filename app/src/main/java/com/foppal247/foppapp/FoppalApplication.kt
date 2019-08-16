package com.foppal247.foppapp

import android.app.Application
import android.util.Log
import com.foppal247.foppapp.domain.LeagueTypes
import java.lang.IllegalStateException

class FoppalApplication : Application() {
    private val TAG = "FoppalApplication"
    var league: LeagueTypes? = LeagueTypes.all
    var country: String? = "Norge"
    var menuGroupId = 0
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: FoppalApplication? = null
        fun getInstance(): FoppalApplication {
            if (appInstance == null) {
                throw IllegalStateException("Configure a classe de Application no AndroidManifest.xml")
            }
            return appInstance!!
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d(TAG, "FoppalApplication.onTerminate()")
    }
}
