package com.example.maichel.submission2.model

import com.google.gson.annotations.SerializedName

data class TeamPlayerModel (
    @SerializedName("strPlayer")
    val strName: String? = null,
    @SerializedName("strDescriptionEN")
    val strDesc: String? = null,
    @SerializedName("strPosition")
    val strPos: String? = null,
    @SerializedName("strThumb")
    val strThumb: String? = null,
    @SerializedName("strCutout")
    val strCutout: String? = null,
    @SerializedName("strHeight")
    val strHeight: String? = null,
    @SerializedName("strWeight")
    val strWeight: String? = null
)