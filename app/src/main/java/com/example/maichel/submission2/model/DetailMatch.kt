package com.example.maichel.submission2.model

import com.google.gson.annotations.SerializedName

data class DetailMatch(
    @SerializedName("idEvent")
    var idEvent: String? = null,
    @SerializedName("strDate")
    var strDate: String? = null,
    @SerializedName("strTime")
    var strTime: String? = null,

    @SerializedName("strHomeTeam")
    var strHomeTeam: String? = null,
    @SerializedName("strAwayTeam")
    var strAwayTeam: String? = null,

    @SerializedName("intHomeScore")
    var intHomeScore: String? = null,
    @SerializedName("intAwayScore")
    var intAwayScore: String? = null,
    @SerializedName("intHomeShots")
    var intHomeShoot: String? = null,
    @SerializedName("intAwayShots")
    var intAwayShoot: String? = null,

    @SerializedName("strHomeGoalDetails")
    var strHomeGoalDetail: String? = null,
    @SerializedName("strAwayGoalDetails")
    var strAwayGoalDetail: String? = null,

    @SerializedName("strHomeLineupGoalkeeper")
    var strHomeLineupGoalkeeper: String? = null,
    @SerializedName("strAwayLineupGoalkeeper")
    var strAwayLineupGoalkeeper: String? = null,

    @SerializedName("strHomeLineupDefense")
    var strHomeLineupDefense: String? = null,
    @SerializedName("strAwayLineupDefense")
    var strAwayLineupDefense: String? = null,

    @SerializedName("strHomeLineupMidfield")
    var strHomeLineupMidfield: String? = null,
    @SerializedName("strAwayLineupMidfield")
    var strAwayLineupMidfield: String? = null,

    @SerializedName("strHomeLineupForward")
    var strHomeLineupForward: String? = null,
    @SerializedName("strAwayLineupForward")
    var strAwayLineupForward: String? = null,

    @SerializedName("strHomeLineupSubstitutes")
    var strHomeLineupSubstitutes: String? = null,
    @SerializedName("strAwayLineupSubstitutes")
    var strAwayLineupSubstitutes: String? = null,

    @SerializedName("strHomeYellowCards")
    var strHomeYellowCards: String? = null,
    @SerializedName("strAwayYellowCards")
    var strAwayYellowCards: String? = null,

    @SerializedName("strHomeRedCards")
    var strHomeRedCards: String? = null,
    @SerializedName("strAwayRedCards")
    var strAwayRedCard: String? = null,


    @SerializedName("idHomeTeam")
    var idHomeTeam: String? = null,
    @SerializedName("idAwayTeam")
    var idAwayTeam: String? = null
)