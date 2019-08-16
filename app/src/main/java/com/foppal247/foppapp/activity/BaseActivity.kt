package com.foppal247.foppapp.activity
import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.foppal247.foppapp.FoppalApplication

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    protected val context: Context get() = this
    protected val foppalInstance : FoppalApplication = FoppalApplication.getInstance()
}
