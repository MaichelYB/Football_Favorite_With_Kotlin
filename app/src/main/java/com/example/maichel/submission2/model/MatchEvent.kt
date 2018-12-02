package com.example.maichel.submission2.model

import com.google.gson.annotations.SerializedName

data class MatchEvent(
    @SerializedName("strEvent")
    var event: String? = null,
    @SerializedName("idEvent")
    var idEvent: String? = null,
    @SerializedName("idLeague")
    var idLeague: String? = null,
    @SerializedName("strSeason")
    var idSeason: String? = null,

    @SerializedName("idHomeTeam")
    var idHomeTeam: String? = null,
    @SerializedName("idAwayTeam")
    var idAwayTeam: String? = null,
    @SerializedName("strHomeTeam")
    var strHomeTeam: String? = null,
    @SerializedName("strAwayTeam")
    var strAwayTeam: String? = null,
    @SerializedName("strDate")
    var strDate: String? = null,
    @SerializedName("strTime")
    var strTime: String? = null,
    @SerializedName("strThumb")
    var strThumb: String? = null,

    @SerializedName("intHomeScore")
    var intHomeScore: String? = null,
    @SerializedName("intAwayScore")
    var intAwayScore: String? = null,
    @SerializedName("intHomeShoot")
    var intHomeShoot: String? = null,
    @SerializedName("intAwayShoot")
    var intAwayShoot: String? = null,

    @SerializedName("strAwayGoalDetails")
    var strAwayGoalDetail: String? = null,
    @SerializedName("strAwayRedCards")
    var strAwayRedCard: String? = null,
    @SerializedName("strAwayYellowCards")
    var strAwayYellowCards: String? = null,
    @SerializedName("strAwayLineupGoalkeeper")
    var strAwayLineupGoalkeeper: String? = null,
    @SerializedName("strAwayLineupDefense")
    var strAwayLineupDefense: String? = null,
    @SerializedName("strAwayLineupMidfield")
    var strAwayLineupMidfield: String? = null,
    @SerializedName("strAwayLineupForward")
    var strAwayLineupForward: String? = null,
    @SerializedName("strAwayLineupSubstitutes")
    var strAwayLineupSubstitutes: String? = null,

    @SerializedName("strHomeGoalDetails")
    var strHomeGoalDetail: String? = null,
    @SerializedName("strHomeRedCards")
    var strHomeRedCards: String? = null,
    @SerializedName("strHomeYellowCards")
    var strHomeYellowCards: String? = null,
    @SerializedName("strHomeLineupGoalkeeper")
    var strHomeLineupGoalkeeper: String? = null,
    @SerializedName("strHomeLineupDefense")
    var strHomeLineupDefense: String? = null,
    @SerializedName("strHomeLineupMidfield")
    var strHomeLineupMidfield: String? = null,
    @SerializedName("strHomeLineupForward")
    var strHomeLineupForward: String? = null,
    @SerializedName("strHomeLineupSubstitutes")
    var strHomeLineupSubstitutes: String? = null
)