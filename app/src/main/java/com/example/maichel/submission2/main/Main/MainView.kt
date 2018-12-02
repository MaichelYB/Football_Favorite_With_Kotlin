package com.example.maichel.submission2.main.Main

import com.example.maichel.submission2.model.MatchEvent

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<MatchEvent>)
    fun showError(message: String)
}