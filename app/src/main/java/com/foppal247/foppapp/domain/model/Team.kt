package com.foppal247.foppapp.domain.model

import com.google.gson.annotations.SerializedName

class Team {
    @SerializedName("team_name")
    var teamName = ""
    @SerializedName("intl_name")
    var intlName = ""
}