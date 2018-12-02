package com.example.maichel.submission2.main.Team.TeamDetail.Presenter

import com.example.maichel.submission2.api.ApiRepository
import com.example.maichel.submission2.api.SportDBApi
import com.example.maichel.submission2.main.Team.TeamDetail.View.PlayerDetailView
import com.example.maichel.submission2.model.TeamPlayerResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerDetailPresenter(private val view: PlayerDetailView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson){
    fun getPlayerDetail(teamName: String){
        view.showTeamDetailLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                .doRequest(SportDBApi.getPlayerDetail(teamName)).await(),
                TeamPlayerResponse::class.java)

            view.hideTeamDetailLoading()
            view.showPlayerList(data.player)
        }
    }
}