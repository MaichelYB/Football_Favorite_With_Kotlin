package com.example.maichel.submission2.main.FavoriteActivity


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.maichel.submission2.R
import com.example.maichel.submission2.database.FavoritesTeam
import com.example.maichel.submission2.database.database
import com.example.maichel.submission2.main.Team.TeamDetail.Activity.TeamDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoriteTeams : Fragment(), AnkoComponent<Context> {
    private val favorites: MutableList<FavoritesTeam> = mutableListOf()
    private lateinit var adapter: FavoriteTeamsAdapter
    private lateinit var listTeam: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = FavoriteTeamsAdapter(favorites){
            context?.startActivity<TeamDetailActivity>(
                "name_home_team" to "${it.teamName}",
                "id_team" to "${it.teamId}",
                "stadium" to "${it.teamStadion}",
                "formed_year" to "${it.teamYear}",
                "team_badge" to "${it.teamBadge}",
                "team_description" to "${it.teamDescription}"
            )
        }
        listTeam.adapter = adapter
        swipeRefresh.onRefresh{
            onFavorites()
        }
    }

    override fun onResume() {
        super.onResume()
        onFavorites()
    }

    private fun onFavorites(){
        favorites.clear()
        context?.database?.use{
            swipeRefresh.isRefreshing = false
            val result = select(FavoritesTeam.TABLE_TEAM_FAVORITE)
            val favorite = result.parseList(classParser<FavoritesTeam>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                listTeam = recyclerView {
                    id = R.id.list_teams_favorite
                    lparams (width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }
}
