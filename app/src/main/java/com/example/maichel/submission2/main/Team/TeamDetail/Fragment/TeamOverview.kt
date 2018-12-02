package com.example.maichel.submission2.main.Team.TeamDetail.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

import com.example.maichel.submission2.R.color.colorAccent
import com.example.maichel.submission2.R.color.colorPrimaryText
import com.example.maichel.submission2.api.ApiRepository
import com.example.maichel.submission2.database.FavoritesTeam
import com.example.maichel.submission2.main.Team.TeamDetail.Presenter.TeamDetailPresenter
import com.example.maichel.submission2.main.Team.TeamDetail.View.TeamDetailView
import com.example.maichel.submission2.model.TeamDetail
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import com.example.maichel.submission2.util.invisible
import com.example.maichel.submission2.util.visible
import com.squareup.picasso.Picasso
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
class TeamOverview : Fragment(), TeamDetailView {
    private val favorites: MutableList<FavoritesTeam> = mutableListOf()
    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: TeamDetail
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamFormedYear: TextView
    private lateinit var teamStadium: TextView
    private lateinit var teamDescription: TextView

    private var id: String = ""
    private var team: String = ""

    companion object {
        fun newInstance(teamId: String): TeamOverview {
            val fragment = TeamOverview()
            val args = Bundle()
            args.putString("idTeam", teamId)
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
        presenter =
                TeamDetailPresenter(this, request, gson)

        return UI{
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                    scrollView {
                        isVerticalScrollBarEnabled = false
                        relativeLayout {
                            lparams(width = matchParent, height = wrapContent)
                            linearLayout {
                                padding = dip(10)
                                orientation = LinearLayout.VERTICAL
                                gravity = Gravity.CENTER_HORIZONTAL

                                linearLayout {
                                    teamBadge =  imageView {
                                    }.lparams(height = dip(75))
                                }

                                linearLayout {
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    teamName = textView{
                                        textSize = 20f
                                        textColor = ContextCompat.getColor(context, colorAccent)
                                    }.lparams{
                                        topMargin = dip(5)
                                    }
                                }

                                linearLayout {
                                    this.gravity = Gravity.CENTER_HORIZONTAL
                                    teamFormedYear = textView{

                                    }
                                }

                                linearLayout {
                                    this.gravity = Gravity.CENTER_HORIZONTAL
                                    teamStadium = textView{
                                        textColor = ContextCompat.getColor(context, colorPrimaryText)
                                    }
                                }

                                linearLayout {
                                    teamDescription = textView{

                                    }
                                }
                            }
                            progressBar = progressBar {
                            }.lparams {
                                centerHorizontally()
                            }
                        }
                    }
                }
            }

            id = arguments?.getString("idTeam", "").toString()

            presenter.getTeamDetail(id)
            swipeRefresh.onRefresh {
                presenter.getTeamDetail(id)
            }
        }.view
    }

    override fun showTeamDetailLoading() {
        progressBar.visible()
    }

    override fun hideTeamDetailLoading() {
        progressBar.invisible()
    }

    override fun showTeamDetailList(data: List<TeamDetail>) {
        teams = TeamDetail(data[0].teamId,
                           data[0].teamName,
                           data[0].teamBadge, data[0].teamFormedYear, data[0].teamStadium, data[0].teamDescription)

        Picasso.get().load(data[0].teamBadge).into(teamBadge)
        teamName.text = data[0].teamName
        teamFormedYear.text = data[0].teamFormedYear
        teamStadium.text = data[0].teamStadium
        teamDescription.text = data[0].teamDescription
    }
}
