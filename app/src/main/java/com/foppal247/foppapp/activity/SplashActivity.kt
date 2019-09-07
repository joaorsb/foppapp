package com.foppal247.foppapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.foppal247.foppapp.R


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
        mDelayHandler!!.postDelayed(mRunnable, splashDelay)
    }

    public override fun onDestroy() {
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }

}