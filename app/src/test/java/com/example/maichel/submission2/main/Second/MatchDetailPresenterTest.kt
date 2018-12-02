package com.example.maichel.submission2.main.Second

import com.example.maichel.submission2.api.ApiRepository
import com.example.maichel.submission2.api.SportDBApi
import com.example.maichel.submission2.main.MatchesList.MatchDetail.Presenter.MatchDetailPresenter
import com.example.maichel.submission2.main.MatchesList.MatchDetail.View.MatchDetailView
import com.example.maichel.submission2.model.DetailMatch
import com.example.maichel.submission2.model.DetailMatchResponse
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`

class MatchDetailPresenterTest {

    @Mock
    private
    lateinit var view: MatchDetailView

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var presenter: MatchDetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = MatchDetailPresenter(
            view,
            apiRepository,
            gson
        )
    }
    @Test
    fun getTeamLeagueDetails() {
        val match: MutableList<DetailMatch> = mutableListOf()
        val response = DetailMatchResponse(match)
        val event = "Tottenham_vs_Chelsea"
        val season = "1819"

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository
                .doRequest(SportDBApi.getDetailTeamMatch(event, season)).await(),
                DetailMatchResponse::class.java
            )).thenReturn(response)

            presenter.getTeamLeagueDetails(event, season)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showTeamList(match)
            Mockito.verify(view).hideLoading()
        }
    }
}