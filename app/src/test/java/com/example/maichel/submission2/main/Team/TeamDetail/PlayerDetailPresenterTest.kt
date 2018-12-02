package com.example.maichel.submission2.main.Team.TeamDetail

import com.example.maichel.submission2.api.ApiRepository
import com.example.maichel.submission2.api.SportDBApi
import com.example.maichel.submission2.main.Team.TeamDetail.Presenter.PlayerDetailPresenter
import com.example.maichel.submission2.main.Team.TeamDetail.View.PlayerDetailView
import com.example.maichel.submission2.model.TeamPlayerModel
import com.example.maichel.submission2.model.TeamPlayerResponse
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayerDetailPresenterTest {

    @Mock
    private
    lateinit var view: PlayerDetailView

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var presenter: PlayerDetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = PlayerDetailPresenter(
            view,
            apiRepository,
            gson
        )
    }
    @Test
    fun getPlayerDetail() {
        val player: MutableList<TeamPlayerModel> = mutableListOf()
        val response = TeamPlayerResponse(player)
        val team = "Chelsea"

        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(SportDBApi.getPlayerDetail(team)).await(),
                    TeamPlayerResponse::class.java
                )
            ).thenReturn(response)

            presenter.getPlayerDetail(team)

            Mockito.verify(view).showTeamDetailLoading()
            Mockito.verify(view).showPlayerList(player)
            Mockito.verify(view).hideTeamDetailLoading()
        }
    }
}