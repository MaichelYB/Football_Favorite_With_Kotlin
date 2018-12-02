package com.example.maichel.submission2.main.Main

import com.example.maichel.submission2.api.ApiRepository
import com.example.maichel.submission2.api.SportDBApi
import com.example.maichel.submission2.model.MatchEventResponse
import com.example.maichel.submission2.model.MatchEvent
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MainPresenterTest {

    @Mock
    private
    lateinit var view: MainView

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var presenter: MainPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view, apiRepository, gson)
    }

    @Test
    fun getn15Match() {
        val nextMatchEvents: MutableList<MatchEvent> = mutableListOf()
        val response = MatchEventResponse(nextMatchEvents)
        val address = "4328"

        GlobalScope.launch{
            `when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(SportDBApi.getn15Match(address)).await(),
                    MatchEventResponse::class.java
                )
            ).thenReturn(response)

            presenter.getn15Match(address)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchList(nextMatchEvents)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getl15Match() {
        val lastMatchEvents: MutableList<MatchEvent> = mutableListOf()
        val response = MatchEventResponse(lastMatchEvents)
        val address = "4328"

        GlobalScope.launch{
            `when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(SportDBApi.getl15Match(address)).await(),
                    MatchEventResponse::class.java
                )
            ).thenReturn(response)

            presenter.getl15Match(address)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchList(lastMatchEvents)
            Mockito.verify(view).hideLoading()
        }
    }
}