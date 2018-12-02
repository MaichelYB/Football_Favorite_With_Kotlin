package com.example.maichel.submission2.main.MatchesList.MatchDetail.View

import com.example.maichel.submission2.model.DetailMatch

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<DetailMatch>)
}