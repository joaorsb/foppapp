package com.foppal247.foppapp.utils

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

object HttpHelper {
    private const val TAG = "http"
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    var client = OkHttpClient()

    fun get(url: String): String {
        val request = Request.Builder().url(url).get().build()
        return getJson(request)
    }

    private fun getJson(request: Request): String {
        val response = client.newCall(request).execute()
        val body = response.body
        if(body != null){
            val json = body.string()
            return json
        }
        throw IOException("Response error")
    }
}