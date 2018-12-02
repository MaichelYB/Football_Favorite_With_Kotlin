package com.example.maichel.submission2.main.Team.TeamDetail.View

import com.example.maichel.submission2.model.TeamDetail

interface TeamDetailView {
    fun showTeamDetailLoading()
    fun hideTeamDetailLoading()
    fun showTeamDetailList(data: List<TeamDetail>)
}