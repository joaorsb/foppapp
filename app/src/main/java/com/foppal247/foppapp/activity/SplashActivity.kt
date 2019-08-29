package com.foppal247.foppapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.foppal247.foppapp.R
import com.foppal247.foppapp.domain.FavoriteTeamsService
import com.foppal247.foppapp.domain.FootballTeamsService
import com.foppal247.foppapp.domain.model.LeagueTypes
import com.foppal247.foppapp.extensions.toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class SplashActivity : BaseActivity() {
    private var mDelayHandler: Handler? = null
    private val splashDelay: Long = 1000

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        mDelayHandler = Handler()
        doAsync {
            //ugly hack to delete teams from db as previous versions saved Eliteserien as Bundesliga Teams
            FootballTeamsService.deleteAllFootballTeamsByLeague(LeagueTypes.bundesliga.leagueName)
            foppalInstance.favoriteTeams = FavoriteTeamsService.getFavoriteTeams()
            uiThread {
                foppalInstance.favoriteTeams.forEach {
                    FirebaseMessaging.getInstance().subscribeToTopic(it.intlName)
                        .addOnCompleteListener { task ->

                        }
                }
            }
        }
        mDelayHandler!!.postDelayed(mRunnable, splashDelay)
    }

    public override fun onDestroy() {
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }

}