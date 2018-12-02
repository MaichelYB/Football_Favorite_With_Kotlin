package com.example.maichel.submission2.model

import com.google.gson.annotations.SerializedName

data class TeamImage (
    @SerializedName("strTeamBadge")
    var teamBadge: String? = null
)