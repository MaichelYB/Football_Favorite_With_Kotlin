package com.example.maichel.submission2.model

import com.google.gson.annotations.SerializedName

data class TeamAwayImage(
    @SerializedName("strTeamBadge")
    var teamAwayBadge: String? = null
)