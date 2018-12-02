package com.example.maichel.submission2.main.Main

import android.content.Context
import android.widget.Toast
import com.example.maichel.submission2.api.ApiRepository
import com.example.maichel.submission2.api.SportDBApi
import com.example.maichel.submission2.model.MatchEventResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainPresenter(
    private val view: MainView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {

    fun getn15Match(league: String?) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(SportDBApi.getn15Match(league)).await(),
                MatchEventResponse::class.java
            )
            view.hideLoading()
            view.showMatchList(data.events)
        }
    }

    fun getl15Match(league: String?) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(SportDBApi.getl15Match(league)).await(),
                MatchEventResponse::class.java
            )
            view.hideLoading()
            view.showMatchList(data.events)
        }
    }

    fun getSearchEvent(query: String){
        view.showLoading()
        GlobalScope.launch (Dispatchers.Main){
            val data = gson.fromJson(
                apiRepository
                    .doRequest(SportDBApi.getSearchTeamMatch(query)).await(),
                MatchEventResponse::class.java
            )
            view.hideLoading()
            if(data == null || data.event.isNullOrEmpty()){
                view.showError("no data")
            } else {
                view.showMatchList(data.event)
            }
        }
    }
}