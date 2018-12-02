package com.example.maichel.submission2.main.Team.TeamHomePage

import com.example.maichel.submission2.api.ApiRepository
import com.example.maichel.submission2.api.SportDBApi
import com.example.maichel.submission2.model.TeamDetailResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamPresenter(
    private val view: TeamView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getTeamList(league: String?){
        view.teamShowLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                .doRequest(SportDBApi.getTeam(league)).await(),
                TeamDetailResponse::class.java)

            view.teamHideLoading()
            view.teamShowDataList(data.teams)
        }
    }

    fun getTeamSearchList(team: String?){
        view.teamShowLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                .doRequest(SportDBApi.getSearchTeam(team)).await(),
                TeamDetailResponse::class.java)

            view.teamHideLoading()
            if(data == null||data.teams.isNullOrEmpty()){
                view.showError("no data")
            }else{
                view.teamShowDataList(data.teams)
            }
        }
    }
}