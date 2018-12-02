package com.example.maichel.submission2.main.ViewPagerHelper

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.maichel.submission2.main.Team.TeamDetail.Fragment.TeamOverview
import com.example.maichel.submission2.main.Team.TeamDetail.Fragment.TeamPlayer

class TeamViewPagerAdapter(fm: FragmentManager, teamId : String): FragmentPagerAdapter(fm) {
    private val pages = listOf(
        TeamOverview.newInstance(teamId),
        TeamPlayer.newInstance(teamId)
    )
    override fun getItem(positon: Int): Fragment {
        return pages[positon] as Fragment
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Team Desc"
            else -> "Players"
        }
    }
}