package com.example.maichel.submission2.main.Team.TeamDetail.View

import com.example.maichel.submission2.model.TeamPlayerModel

interface PlayerDetailView {
    fun showTeamDetailLoading()
    fun hideTeamDetailLoading()
    fun showPlayerList(data: List<TeamPlayerModel>)
}