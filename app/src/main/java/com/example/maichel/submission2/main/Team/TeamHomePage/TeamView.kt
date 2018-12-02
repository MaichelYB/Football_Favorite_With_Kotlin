package com.example.maichel.submission2.main.Team.TeamHomePage

import com.example.maichel.submission2.model.TeamDetail

interface TeamView {
    fun teamShowLoading()
    fun teamHideLoading()
    fun teamShowDataList(data: List<TeamDetail>)
    fun showError(message: String)
}