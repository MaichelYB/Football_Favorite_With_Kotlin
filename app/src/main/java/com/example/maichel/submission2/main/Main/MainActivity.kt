package com.example.maichel.submission2.main.Main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.example.maichel.submission2.main.MatchesList.MatchesActivity.Fragment.MatchParentFragment
import com.example.maichel.submission2.R
import com.example.maichel.submission2.R.id.*
import com.example.maichel.submission2.Event.SearchViewEvent
import com.example.maichel.submission2.main.FavoriteActivity.FavoriteParentFragment
import com.example.maichel.submission2.main.Team.TeamHomePage.ActivityTeamFragment
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus


class MainActivity : AppCompatActivity() {

    lateinit var mSearchView: MaterialSearchView
    private var isFavorites: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        mSearchView = search_view
        getSearchView()
        supportActionBar?.title = "Football App"
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                match ->{
                    loadMatchesActivity(savedInstanceState)
                }
                teams ->{
                    loadTeamsActivity(savedInstanceState)
                }
                favorites ->{
                    loadFavoritesActivity(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = match
    }

    private fun getSearchView() {
        mSearchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //Do some magic
                EventBus.getDefault().post(SearchViewEvent(query))
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                //Do some magic
                if(query.length>=3){
                    EventBus.getDefault().post(SearchViewEvent(query))
                }
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.action_search)
        mSearchView.setMenuItem(item)
        return true
    }
    private fun loadMatchesActivity(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container,
                    MatchParentFragment(), MatchParentFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadTeamsActivity(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, ActivityTeamFragment(), ActivityTeamFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavoritesActivity(savedInstanceState: Bundle?){

        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, FavoriteParentFragment(), FavoriteParentFragment::class.java.simpleName)
                .commit()
        }
    }
}