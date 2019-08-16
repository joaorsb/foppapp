package com.foppal247.foppapp.domain

import com.google.gson.annotations.SerializedName

class News {
    var url = ""
    var headline = ""
    @SerializedName("source_url")
    var sourceUrl = ""
    @SerializedName("publishing_date")
    var publishingDate = ""
    var language = ""
}