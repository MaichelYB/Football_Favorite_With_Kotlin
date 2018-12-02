package com.example.maichel.submission2.main.ViewPagerHelper

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.maichel.submission2.main.MatchesList.MatchesActivity.Fragment.Last15Match
import com.example.maichel.submission2.main.MatchesList.MatchesActivity.Fragment.NextMatchFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val pages = listOf(
        NextMatchFragment(),
        Last15Match()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position] as Fragment
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Next 15 Match"
            else -> "Last 15 Match"
        }
    }
}