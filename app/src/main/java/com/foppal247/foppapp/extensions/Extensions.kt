package com.foppal247.foppapp.extensions

import androidx.appcompat.app.ActionBar
import android.app.Activity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


//findViewById + setOnClickListener
fun AppCompatActivity.onClick(@IdRes viewId: Int, onClick: (v: View?) -> Unit) {
    val view = findViewById<View>(viewId)
    view.setOnClickListener { onClick(it) }
}

// Mostra toast
fun Activity.toast(message: CharSequence, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, length).show()

// getString
fun AppCompatActivity.getTextString(@IdRes id: Int): String {
    val textView = findViewById<TextView>(id)
    val s = textView.text.toString()
    return s
}

//Configura o Toolbar
fun AppCompatActivity.setupToolbar(@IdRes id: Int, title: String? = null,
                                   upNavigation:Boolean = false): ActionBar {
    val toolbar = findViewById<Toolbar>(id)
    setSupportActionBar(toolbar)
    if(title != null) {
        supportActionBar?.title = title
    }
    supportActionBar?.setDisplayHomeAsUpEnabled(upNavigation)
    return supportActionBar!!
}

//Configura fragment
fun AppCompatActivity.addFragment(@IdRes layoutId: Int, fragment: Fragment) {
    fragment.arguments = intent.extras
    val ft = supportFragmentManager.beginTransaction()
    ft.add(layoutId, fragment)
    ft.commit()
}

//Converter para Json
fun Any.toJson(prettyPrinting: Boolean = false): String {
    var builder = GsonBuilder()
    if(prettyPrinting){
        builder.setPrettyPrinting()
    }
    val json = builder.create().toJson(this)
    return json
}

//Json para objeto
inline fun <reified T> Any.fromJson(json: String): T {
    val type = object: TypeToken<T>() {}.type
    return Gson().fromJson<T>(json, type)
}