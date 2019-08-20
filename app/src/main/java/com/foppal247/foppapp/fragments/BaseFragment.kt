package com.foppal247.foppapp.fragments

import androidx.fragment.app.Fragment
import com.foppal247.foppapp.R
import com.foppal247.foppapp.utils.AndroidUtils
import org.jetbrains.anko.support.v4.toast

open class BaseFragment : Fragment() {
    var hasConnection : Boolean = false

    fun checkConnection() {
        if (AndroidUtils.isNetworkAvailable(context)) {
            hasConnection = true
        } else {
            var errorMessage = context?.getText(R.string.noInternet)
            toast(errorMessage!!)
        }
    }
}