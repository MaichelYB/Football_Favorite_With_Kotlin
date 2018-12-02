package com.example.maichel.submission2.main.Team.TeamDetail.Presenter

import com.example.maichel.submission2.api.ApiRepository
import com.example.maichel.submission2.api.SportDBApi
import com.example.maichel.submission2.main.Team.TeamDetail.View.TeamDetailView
import com.example.maichel.submission2.model.TeamDetailResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamDetailPresenter (private val view: TeamDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson){
    fun getTeamDetail(teamId: String){
        view.showTeamDetailLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                .doRequest(SportDBApi.getTeamDetail(teamId)).await(),
                TeamDetailResponse::class.java)

            view.hideTeamDetailLoading()
            view.showTeamDetailList(data.teams)
        }
    }
}