package com.foppal247.foppapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.foppal247.foppapp.R
import com.foppal247.foppapp.domain.FavoriteTeamsService
import com.foppal247.foppapp.domain.model.FavoriteTeam
import com.foppal247.foppapp.extensions.toast
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.SubscriptionEventListener
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.json.JSONObject


class SplashActivity : BaseActivity() {
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1000

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
        //Initialize the Handler
        mDelayHandler = Handler()
        doAsync {
            foppalInstance.favoriteTeams = FavoriteTeamsService.getFavoriteTeams()
//            adicionar canais do Pusher aqui? Não funcionou, provavelmente é melhor adicionar
//            em outra classe.
//            if(foppalInstance.favoriteTeams.isNotEmpty()){
//                foppalInstance.favoriteTeams.forEach {
//                    setupPusher(it.intlName)
//                }
//            }
        }

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    private fun setupPusher(teamName: String) {
        val options = PusherOptions()
        options.setCluster("us2")

        val pusher = Pusher("f02a86d7e2321f93cdbf", options)
        val channel = pusher.subscribe(teamName)

        channel.run {

            bind(
                "new-message"
            ) {
                fun onEvent(channelName: String, eventName: String, data: String) {
                    val json = JSONObject(data)
                    toast(json["team_name"].toString())
                    Log.d("Pusher", data)
                }
            }
        }

        pusher.connect()
    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

}