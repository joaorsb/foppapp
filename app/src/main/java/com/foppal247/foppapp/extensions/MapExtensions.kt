package com.foppal247.foppapp.extensions

import android.os.Bundle

fun Map<String, String>.ToBundle(): Bundle {
    val bundle = Bundle()
    for(key in keys){
        bundle.putString(key, get(key))
    }
    return bundle
}