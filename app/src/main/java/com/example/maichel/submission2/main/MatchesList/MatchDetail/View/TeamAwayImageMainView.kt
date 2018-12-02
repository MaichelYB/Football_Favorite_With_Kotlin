package com.example.maichel.submission2.main.MatchesList.MatchDetail.View

import com.example.maichel.submission2.model.TeamAwayImage

interface TeamAwayImageMainView {
    fun showImageAwayLoading()
    fun hideImageAwayLoading()
    fun showImageAway(data: List<TeamAwayImage>)
}