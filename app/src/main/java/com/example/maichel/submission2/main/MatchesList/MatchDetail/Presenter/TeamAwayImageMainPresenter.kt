package com.example.maichel.submission2.main.MatchesList.MatchDetail.Presenter

import com.example.maichel.submission2.api.ApiRepository
import com.example.maichel.submission2.api.SportDBApi
import com.example.maichel.submission2.main.MatchesList.MatchDetail.View.TeamAwayImageMainView
import com.example.maichel.submission2.model.TeamAwayImageResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamAwayImageMainPresenter(
    private val viewTeamAway: TeamAwayImageMainView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {

    fun getTeamAway(TeamID: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(SportDBApi.getTeamBadge(TeamID)).await(),
                TeamAwayImageResponse::class.java
            )
            viewTeamAway.hideImageAwayLoading()
            viewTeamAway.showImageAway(data.teams)
        }
    }
}