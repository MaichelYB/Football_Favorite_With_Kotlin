package com.example.maichel.submission2.main.Team.TeamDetail.Activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.maichel.submission2.R
import com.example.maichel.submission2.R.drawable.ic_add_to_favorite
import com.example.maichel.submission2.R.drawable.ic_added_to_favorite
import com.example.maichel.submission2.R.id.add_to_favorite
import com.example.maichel.submission2.R.menu.detail_menu
import com.example.maichel.submission2.api.ApiRepository
import com.example.maichel.submission2.database.FavoritesTeam
import com.example.maichel.submission2.database.database
import com.example.maichel.submission2.main.Team.TeamDetail.Presenter.TeamDetailPresenter
import com.example.maichel.submission2.main.Team.TeamDetail.View.TeamDetailView
import com.example.maichel.submission2.main.ViewPagerHelper.TeamViewPagerAdapter
import com.example.maichel.submission2.model.TeamDetail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_match_parent.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.*

class TeamDetailActivity: AppCompatActivity(),
    TeamDetailView {
    private var id : String = ""
    private var nameTeam: String = ""
    private lateinit var teams: TeamDetail
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null
    private lateinit var presenter: TeamDetailPresenter
    private lateinit var progressBar: ProgressBar
    val request = ApiRepository()
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_layout)

        val intent = intent
        id = intent.getStringExtra("id_team")
        nameTeam = intent.getStringExtra("name_home_team")

        supportActionBar?.title = nameTeam
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewPager = findViewById<View>(R.id.viewPager_main) as ViewPager
        viewPager.adapter =
                TeamViewPagerAdapter(supportFragmentManager, id)
        tabs_main.setupWithViewPager(viewPager_main)

        presenter =
                TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(id)
        favoriteState()
    }

    override fun showTeamDetailLoading() {
    }

    override fun hideTeamDetailLoading() {
    }

    override fun showTeamDetailList(data: List<TeamDetail>) {
        teams = TeamDetail(data[0].teamId, data[0].teamName, data[0].teamBadge, data[0].teamFormedYear,
                           data[0].teamStadium, data[0].teamDescription)
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoritesTeam.TABLE_TEAM_FAVORITE)
                .whereArgs("(TEAM_ID = {id})",
                    "id" to id)
            val favorite = result.parseList(classParser<FavoritesTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(FavoritesTeam.TABLE_TEAM_FAVORITE,
                    FavoritesTeam.TEAM_ID to teams.teamId,
                    FavoritesTeam.TEAM_NAME to teams.teamName,
                    FavoritesTeam.TEAM_STADION to teams.teamStadium,
                    FavoritesTeam.TEAM_DESCRIPTION to teams.teamDescription,
                    FavoritesTeam.TEAM_BADGE to teams.teamBadge)
            }
            Toast.makeText(ctx, "Added to favorite", Toast.LENGTH_LONG).show()
            //swipeRefresh.snackbar("Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            Toast.makeText(ctx, e.localizedMessage, Toast.LENGTH_LONG).show()
            //swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoritesTeam.TABLE_TEAM_FAVORITE, "(TEAM_ID = {id})",
                    "id" to id)
            }
            Toast.makeText(ctx, "Remove from Favorite", Toast.LENGTH_LONG).show()
        } catch (e: SQLiteConstraintException){
            Toast.makeText(ctx, e.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorite)
    }
}