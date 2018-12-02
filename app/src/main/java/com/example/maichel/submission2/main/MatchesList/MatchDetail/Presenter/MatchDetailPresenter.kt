package com.example.maichel.submission2.main.MatchesList.MatchDetail.Presenter

import com.example.maichel.submission2.api.ApiRepository
import com.example.maichel.submission2.api.SportDBApi
import com.example.maichel.submission2.main.MatchesList.MatchDetail.View.MatchDetailView
import com.example.maichel.submission2.model.DetailMatchResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchDetailPresenter(
    private val view: MatchDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getTeamLeagueDetails(TeamLeague: String, season: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {

            val data = gson.fromJson(apiRepository
                .doRequest(SportDBApi.getDetailTeamMatch(TeamLeague, season)).await(),
                DetailMatchResponse::class.java
            )
            view.hideLoading()
            view.showTeamList(data.event)
        }
    }
}