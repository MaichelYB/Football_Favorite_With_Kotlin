package com.example.maichel.submission2.main.MatchesList.MatchDetail.View

import com.example.maichel.submission2.model.TeamImage

interface TeamHomeImageMainView {
    fun showImageLoading()
    fun hideImageLoading()
    fun showTeamImageList(data: List<TeamImage>)
}