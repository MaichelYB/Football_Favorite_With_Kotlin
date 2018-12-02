package com.example.maichel.submission2.main.ViewPagerHelper

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.maichel.submission2.main.FavoriteActivity.FavoriteTeams
import com.example.maichel.submission2.main.FavoriteActivity.FavoritesMatch

class ViewPagerFavorite(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val pages = listOf(
        FavoritesMatch(),
        FavoriteTeams()
    )
    override fun getItem(position: Int): Fragment {
        return pages[position] as Fragment
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "FavoritesMatch Match"
            else -> "FavoritesMatch Team"
        }
    }
}