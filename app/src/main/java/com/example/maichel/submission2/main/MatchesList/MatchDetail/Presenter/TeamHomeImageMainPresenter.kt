package com.example.maichel.submission2.main.MatchesList.MatchDetail.Presenter

import com.example.maichel.submission2.api.ApiRepository
import com.example.maichel.submission2.api.SportDBApi
import com.example.maichel.submission2.main.MatchesList.MatchDetail.View.TeamHomeImageMainView
import com.example.maichel.submission2.model.TeamHomeImageResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamHomeImageMainPresenter(
    private val viewTeamHome: TeamHomeImageMainView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getTeamHomeDetail(TeamID: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(SportDBApi.getTeamBadge(TeamID)).await(),
                TeamHomeImageResponse::class.java
            )
            viewTeamHome.hideImageLoading()
            viewTeamHome.showTeamImageList(data.teams)
        }
    }
}