package com.example.maichel.submission2.main.Team.TeamDetail.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

import com.example.maichel.submission2.R
import com.example.maichel.submission2.api.ApiRepository
import com.example.maichel.submission2.main.Team.TeamDetail.Activity.PlayerBio
import com.example.maichel.submission2.main.Team.TeamDetail.Adapter.PlayerAdapter
import com.example.maichel.submission2.main.Team.TeamDetail.Presenter.PlayerDetailPresenter
import com.example.maichel.submission2.main.Team.TeamDetail.View.PlayerDetailView
import com.example.maichel.submission2.model.TeamPlayerModel
import com.example.maichel.submission2.util.invisible
import com.example.maichel.submission2.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
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
class TeamPlayer : Fragment(), PlayerDetailView {

    private lateinit var presenter: PlayerDetailPresenter
    private var player: MutableList<TeamPlayerModel> = mutableListOf()
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var listPlayer: RecyclerView
    private lateinit var adapter: PlayerAdapter
    private lateinit var playerImage: ImageView
    private lateinit var playerName: TextView
    private lateinit var playerPositon: TextView

    private var nameTeam: String = ""

    companion object {
        fun newInstance(teamId: String): TeamPlayer {
            val fragment = TeamPlayer()
            val args = Bundle()
            args.putString("teamName", teamId)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerDetailPresenter(
            this,
            request,
            gson
        )

        return UI{
            linearLayout {
                adapter = PlayerAdapter(player) {
                    startActivity<PlayerBio>(
                        "player_name" to "${it.strName}",
                        "player_pict" to "${it.strThumb}",
                        "player_height" to "${it.strHeight}",
                        "player_weight" to "${it.strWeight}",
                        "player_desc" to "${it.strDesc}"
                    )
                }
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)
                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(
                        R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)
                        listPlayer = recyclerView {
                            id = R.id.list_last_match
                            lparams(width = matchParent, height = wrapContent){
                                bottomPadding = dip(50)
                            }
                            layoutManager = LinearLayoutManager(ctx)
                            adapter = adapter
                        }

                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }

                }
            }

            listPlayer.adapter = adapter
            nameTeam = arguments?.getString("teamName", "").toString()

            presenter.getPlayerDetail(nameTeam)
            swipeRefresh.onRefresh {
                presenter.getPlayerDetail(nameTeam)
            }
        }.view
    }

    override fun showTeamDetailLoading() {
        progressBar.visible()
    }

    override fun hideTeamDetailLoading() {
        progressBar.invisible()
    }

    override fun showPlayerList(data: List<TeamPlayerModel>) {
        swipeRefresh.isRefreshing = false
        player.clear()
        player.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
