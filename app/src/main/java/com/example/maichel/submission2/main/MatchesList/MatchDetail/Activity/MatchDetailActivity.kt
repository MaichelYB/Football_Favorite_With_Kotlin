package com.example.maichel.submission2.main.MatchesList.MatchDetail.Activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.maichel.submission2.R
import com.example.maichel.submission2.R.color.colorAccent
import com.example.maichel.submission2.R.drawable.ic_add_to_favorite
import com.example.maichel.submission2.R.drawable.ic_added_to_favorite
import com.example.maichel.submission2.R.id.add_to_favorite
import com.example.maichel.submission2.api.ApiRepository
import com.example.maichel.submission2.database.FavoritesMatch
import com.example.maichel.submission2.database.database
import com.example.maichel.submission2.main.MatchesList.MatchDetail.Presenter.MatchDetailPresenter
import com.example.maichel.submission2.main.MatchesList.MatchDetail.View.MatchDetailView
import com.example.maichel.submission2.main.MatchesList.MatchDetail.Presenter.TeamAwayImageMainPresenter
import com.example.maichel.submission2.main.MatchesList.MatchDetail.View.TeamAwayImageMainView
import com.example.maichel.submission2.main.MatchesList.MatchDetail.Presenter.TeamHomeImageMainPresenter
import com.example.maichel.submission2.main.MatchesList.MatchDetail.View.TeamHomeImageMainView
import com.example.maichel.submission2.model.DetailMatch
import com.example.maichel.submission2.model.TeamAwayImage
import com.example.maichel.submission2.model.TeamImage
import com.example.maichel.submission2.util.formatDate
import com.example.maichel.submission2.util.invisible
import com.example.maichel.submission2.util.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MatchDetailActivity : AppCompatActivity(),
    MatchDetailView,
    TeamHomeImageMainView,
    TeamAwayImageMainView {
    private var detailMatch: MutableList<DetailMatch> = mutableListOf()
    private var teamBadge: MutableList<TeamImage> = mutableListOf()
    private var teamAwayBadge: MutableList<TeamAwayImage> = mutableListOf()
    private lateinit var secondPresenter: MatchDetailPresenter
    private lateinit var teamHomeImagePresenter: TeamHomeImageMainPresenter
    private lateinit var teamAwayImagePresenter: TeamAwayImageMainPresenter
    //private lateinit var adapter: SecondActivityAdapter

    private lateinit var teamAwayPict: ImageView
    private lateinit var teamHomePict: ImageView
    private lateinit var teamHomeName: TextView
    private lateinit var teamAwayName: TextView
    private lateinit var date: TextView
    private lateinit var time: TextView
    private lateinit var teamHomeGoal: TextView
    private lateinit var teamAwayGoal: TextView
    private lateinit var separator: TextView
    private lateinit var teamHomeShoot: TextView
    private lateinit var teamAwayShoot: TextView
    private lateinit var goalDetailHome: TextView
    private lateinit var goalDetailAway: TextView
    private lateinit var yellowCardHome: TextView
    private lateinit var yellowCardAway: TextView
    private lateinit var redCardHome: TextView
    private lateinit var redCardAway: TextView
    private lateinit var formationHome: TextView
    private lateinit var formationAway: TextView
    private lateinit var homeGoalKeeper: TextView
    private lateinit var awayGoalKeeper: TextView
    private lateinit var homeDef: TextView
    private lateinit var awayDef: TextView
    private lateinit var homeMid: TextView
    private lateinit var awayMid: TextView
    private lateinit var homeFront: TextView
    private lateinit var awayFront: TextView
    private lateinit var homeSubs: TextView
    private lateinit var awaySubs: TextView

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var matchEvent: DetailMatch
    private lateinit var homeImage: TeamImage
    private lateinit var awayImage: TeamAwayImage
    private var season: String = ""
    private var idEvent: String = ""
    private var nameHomeTeam: String = ""
    private var nameAwayTeam: String = ""
    private var idHomeTeam: String = ""
    private var idAwayTeam: String = ""
    private var isAcces: Boolean = false
    private var isHome: Boolean = false
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            bottomPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                scrollView{
                    isVerticalScrollBarEnabled = false
                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        padding = dip(5)
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER_HORIZONTAL
                        //logo team
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            relativeLayout {
                                teamHomePict = imageView {
                                }.lparams(width = dip(70), height = dip(70)) {
                                    setMargins(0, 20, 100, 10)
                                }
                            }
                            relativeLayout {
                                teamAwayPict = imageView {
                                }.lparams(width = dip(70), height = dip(70)) {
                                    setMargins(80, 20, 0, 30)
                                }
                            }
                        }
                        //nama team
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            teamHomeName = textView {
                                textSize = 15f
                                singleLine = true
                                gravity = Gravity.START
                                ellipsize = TextUtils.TruncateAt.END
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }
                            teamHomeGoal = textView {
                                textSize = 15f
                            }.lparams {
                                topMargin = dip(10)
                                weight = 0.1F
                            }
                            separator = textView {
                                id = R.id.separator
                                textSize = 15f
                            }.lparams {
                                topMargin = dip(10)
                                weight = 0.1F
                            }
                            teamAwayGoal = textView {
                                id = R.id.team_away_score
                                textSize = 15f
                            }.lparams {
                                topMargin = dip(10)
                                weight = 0.1F
                            }
                            teamAwayName = textView {
                                id = R.id.team_away_name
                                textSize = 15f
                                gravity = Gravity.END
                                ellipsize = TextUtils.TruncateAt.END
                                singleLine = true
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }
                        }
                        //tanggal
                        linearLayout {
                            gravity = Gravity.CENTER_HORIZONTAL
                            date = textView {
                                id = R.id.date_match
                                textSize = 16F
                            }.lparams {
                                margin = dip(15)
                            }
                        }
                        //waktu
                        linearLayout {
                            gravity = Gravity.CENTER_HORIZONTAL
                            time = textView {
                                id = R.id.time_match
                                textSize = 16F
                            }.lparams {
                                margin = dip(15)
                            }
                        }
                        //formasi
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            formationHome = textView {
                                id = R.id.formation_home
                                textSize = 14f
                                gravity = Gravity.START
                                singleLine = true
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }

                            textView {
                                textResource = R.string.formation
                                textSize = 14f
                                gravity = Gravity.CENTER_HORIZONTAL
                            }.lparams {
                                topMargin = dip(10)
                                weight = 0.1F
                            }

                            formationAway = textView {
                                id = R.id.formation_away
                                textSize = 14f
                                gravity = Gravity.END
                                singleLine = true
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }
                        }
                        //total shoot
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F

                            teamHomeShoot = textView {
                                id = R.id.team_home_shoot
                                textSize = 14f
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }

                            textView {
                                textResource = R.string.totalShoot
                                gravity = Gravity.CENTER_HORIZONTAL
                                textSize = 14f
                            }.lparams {
                                topMargin = dip(10)
                                weight = 0.1F
                            }

                            teamAwayShoot = textView {
                                id = R.id.team_away_shoot
                                textSize = 14f
                                gravity = Gravity.END
                                width = 0
                                ellipsize = TextUtils.TruncateAt.END
                                setPadding(5, 5, dip(15), 5)
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }
                        }
                        //pencetak goal
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            goalDetailHome = textView {
                                id = R.id.team_home_goal_shooter
                                textSize = 10f
                                gravity = Gravity.START
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }

                            textView {
                                textResource = R.string.goalShooter
                                gravity = Gravity.CENTER_HORIZONTAL
                                textSize = 14f
                            }.lparams {
                                topMargin = dip(10)
                                weight = 0.1F
                            }

                            goalDetailAway = textView {
                                id = R.id.team_away_goal_shooter
                                textSize = 10f
                                gravity = Gravity.END
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }
                        }
                        //kartu kuning
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            yellowCardHome = textView {
                                id = R.id.team_home_yellow
                                textSize = 14f
                                gravity = Gravity.START
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }

                            textView {
                                textResource = R.string.yellowCard
                                gravity = Gravity.CENTER_HORIZONTAL
                                textSize = 14f
                            }.lparams {
                                topMargin = dip(10)
                                weight = 0.1F
                            }

                            yellowCardAway = textView {
                                id = R.id.team_away_yellow
                                textSize = 14f
                                gravity = Gravity.END
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }
                        }
                        //kartu merah
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            redCardHome = textView {
                                id = R.id.team_home_red
                                textSize = 14f
                                gravity = Gravity.START
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }

                            textView {
                                textResource = R.string.redCard
                                gravity = Gravity.CENTER_HORIZONTAL
                                textSize = 14f
                            }.lparams {
                                topMargin = dip(10)
                                weight = 0.1F
                            }

                            redCardAway = textView {
                                id = R.id.team_away_red
                                textSize = 14f
                                gravity = Gravity.END
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }
                        }
                        //kiper
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            homeGoalKeeper = textView {
                                id = R.id.team_home_keeper
                                textSize = 14f
                                gravity = Gravity.START
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }

                            textView {
                                textResource = R.string.goalKeeper
                                gravity = Gravity.CENTER_HORIZONTAL
                                textSize = 14f
                            }.lparams {
                                topMargin = dip(10)
                                weight = 0.1F
                            }

                            awayGoalKeeper = textView {
                                id = R.id.team_away_keeper
                                textSize = 14f
                                gravity = Gravity.END
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }
                        }
                        //defender
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            homeDef = textView {
                                id = R.id.team_home_back
                                textSize = 14f
                                gravity = Gravity.START
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }

                            textView {
                                gravity = Gravity.CENTER_HORIZONTAL
                                textResource = R.string.def
                                textSize = 14f
                            }.lparams {
                                topMargin = dip(10)
                                weight = 0.1F
                            }

                            awayDef = textView {
                                id = R.id.team_away_back
                                textSize = 14f
                                gravity = Gravity.END
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }
                        }
                        //midfield
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            homeMid = textView {
                                id = R.id.team_home_mid
                                textSize = 14f
                                gravity = Gravity.START
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }

                            textView {
                                textResource = R.string.mid
                                gravity = Gravity.CENTER_HORIZONTAL
                                textSize = 14f
                            }.lparams {
                                topMargin = dip(10)
                                weight = 0.1F
                            }

                            awayMid = textView {
                                id = R.id.team_away_mid
                                textSize = 14f
                                gravity = Gravity.END
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }
                        }
                        //foward
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            homeFront = textView {
                                id = R.id.team_home_front
                                textSize = 14f
                                gravity = Gravity.START
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }

                            textView {
                                textResource = R.string.atk
                                gravity = Gravity.CENTER_HORIZONTAL
                                textSize = 14f
                            }.lparams {
                                topMargin = dip(10)
                                weight = 0.1F
                            }

                            awayFront = textView {
                                id = R.id.team_away_front
                                textSize = 14f
                                gravity = Gravity.END
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }
                        }
                        //subtitute
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(3)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 3F
                            homeSubs = textView {
                                id = R.id.team_home_subtitute
                                textSize = 14f
                                gravity = Gravity.START
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }

                            textView {
                                textResource = R.string.subtitutes
                                textSize = 14f
                            }.lparams {
                                topMargin = dip(10)
                                weight = 0.1F
                            }

                            awaySubs = textView {
                                id = R.id.team_away_subtitute
                                textSize = 14f
                                gravity = Gravity.END
                                width = 0
                                setPadding(5, 5, dip(15), 5)
                                ellipsize = TextUtils.TruncateAt.END
                            }.lparams {
                                topMargin = dip(10)
                                weight = 1.35F
                            }
                        }
                    }
                }

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listTeam = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(context)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }


        /*adapter = SecondActivityAdapter(detailMatch, teamBadge, teamAwayBadge)
        listTeam.adapter = adapter*/

        val intent = intent
        val detailTeam = DetailMatch()
        season = intent.getStringExtra("season")
        nameHomeTeam = intent.getStringExtra("name_home_team")
        nameAwayTeam = intent.getStringExtra("name_away_team")
        idAwayTeam = intent.getStringExtra("id_away_team")
        idHomeTeam = intent.getStringExtra("id_home_team")
        idEvent = intent.getStringExtra("id_event")

        val nameHomeTeam2 = nameHomeTeam.replace(" ", "%20")
        val nameAwayTeam2 = nameAwayTeam.replace(" ", "%20")

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        secondPresenter = MatchDetailPresenter(
            this,
            request,
            gson
        )
        teamHomeImagePresenter =
                TeamHomeImageMainPresenter(
                    this,
                    request,
                    gson
                )
        teamAwayImagePresenter =
                TeamAwayImageMainPresenter(
                    this,
                    request,
                    gson
                )
        //get league detail
        val alamat = "${nameHomeTeam2}_vs_${nameAwayTeam2}"
        val season = season
        //get logo hometeam
        val teamHomeId = idHomeTeam
        //get logo awayteam
        val teamAwayId = idAwayTeam

        secondPresenter.getTeamLeagueDetails(alamat, season)
        teamHomeImagePresenter.getTeamHomeDetail(teamHomeId)
        teamAwayImagePresenter.getTeamAway(teamAwayId)

        swipeRefresh.onRefresh {
            secondPresenter.getTeamLeagueDetails(alamat, season)
            isAcces = true
            if (isAcces) {
                teamHomeImagePresenter.getTeamHomeDetail(teamHomeId)
                isHome = true
            }
            if (isHome) {
                teamAwayImagePresenter.getTeamAway(teamAwayId)
            }
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoritesMatch.TABLE_FAVORITE)
                .whereArgs("MATCH_ID = {id}",
                    "id" to idEvent)
            val favorite = result.parseList(classParser<FavoritesMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showImageLoading() {
        progressBar.visible()
    }

    override fun hideImageLoading() {
        progressBar.invisible()
    }

    override fun showImageAwayLoading() {
        progressBar.visible()
    }

    override fun hideImageAwayLoading() {
        progressBar.invisible()
    }

    override fun showImageAway(data: List<TeamAwayImage>) {
        awayImage = TeamAwayImage(data[0].teamAwayBadge)
        swipeRefresh.isRefreshing = false
        Picasso.get().load(data[0].teamAwayBadge).into(teamAwayPict)
    }

    override fun showTeamList(data: List<DetailMatch>) {
        matchEvent = DetailMatch(data[0].idHomeTeam,
                                 data[0].strHomeTeam,
                                data[0].strAwayTeam,
                                data[0].strDate,
                                data[0].strTime,
                                data[0].intHomeScore,
                                data[0].intAwayScore,
                                data[0].intHomeShoot,
                                data[0].intAwayShoot,
                                data[0].strHomeYellowCards,
                                data[0].strAwayYellowCards,
                                data[0].strHomeRedCards,
                                data[0].strAwayRedCard,
                                data[0].strHomeGoalDetail,
                                data[0].strAwayGoalDetail,
                                data[0].strHomeLineupGoalkeeper,
                                data[0].strAwayLineupGoalkeeper,
                                data[0].strHomeLineupDefense,
                                data[0].strAwayLineupDefense,
                                data[0].strHomeLineupMidfield,
                                data[0].strAwayLineupMidfield,
                                data[0].strHomeLineupForward,
                                data[0].strAwayLineupForward,
                                data[0].strAwayLineupSubstitutes,
                                data[0].strHomeLineupSubstitutes)

        teamHomeName.text = data[0].strHomeTeam
        teamAwayName.text = data[0].strAwayTeam
        date.text = data[0].strDate?.formatDate()
        time.text = data[0].strTime

        if(data[0].intAwayScore == null){
            teamHomeGoal.textResource = R.string.separator
            teamAwayGoal.textResource = R.string.separator
            separator.textResource = R.string.versus
            teamHomeShoot.textResource = R.string.separator
            teamAwayShoot.textResource = R.string.separator
            goalDetailHome.textResource = R.string.separator
            goalDetailAway.textResource = R.string.separator
            yellowCardHome.textResource = R.string.separator
            yellowCardAway.textResource = R.string.separator
            redCardHome.textResource = R.string.separator
            redCardAway.textResource = R.string.separator
            formationHome.textResource = R.string.separator
            formationAway.textResource = R.string.separator
            homeGoalKeeper.textResource = R.string.separator
            awayGoalKeeper.textResource = R.string.separator
            homeDef.textResource = R.string.separator
            awayDef.textResource = R.string.separator
            homeMid.textResource = R.string.separator
            awayMid.textResource = R.string.separator
            homeFront.textResource = R.string.separator
            awayFront.textResource = R.string.separator
            homeSubs.textResource = R.string.separator
            awaySubs.textResource = R.string.separator
        }else{
            val awayback: Int =
                ((data[0].strAwayLineupDefense).toString()).length - ((data[0].strAwayLineupDefense).toString().replace(
                    ";",
                    ""
                )).length
            val awaymid: Int =
                ((data[0].strAwayLineupMidfield).toString()).length - ((data[0].strAwayLineupMidfield).toString().replace(
                    ";",
                    ""
                )).length
            val awayfront: Int =
                ((data[0].strAwayLineupForward).toString()).length - ((data[0].strAwayLineupForward).toString().replace(
                    ";",
                    ""
                )).length

            val homeback: Int =
                ((data[0].strHomeLineupDefense).toString()).length - ((data[0].strHomeLineupDefense).toString().replace(
                    ";",
                    ""
                )).length
            val homemid: Int =
                ((data[0].strHomeLineupMidfield).toString()).length - ((data[0].strHomeLineupMidfield).toString().replace(
                    ";",
                    ""
                )).length
            val homefront: Int =
                ((data[0].strHomeLineupForward).toString()).length - ((data[0].strHomeLineupForward).toString().replace(
                    ";",
                    ""
                )).length
            teamHomeName.text = data[0].strHomeTeam
            teamAwayName.text = data[0].strAwayTeam
            teamHomeGoal.text = data[0].intHomeScore
            teamAwayGoal.text = data[0].intAwayScore
            separator.textResource = R.string.separator
            teamHomeShoot.text = data[0].intHomeShoot
            teamAwayShoot.text = data[0].intAwayShoot
            goalDetailHome.text = data[0].strHomeGoalDetail.toString().replace(";", "\n\n")
            goalDetailAway.text = data[0].strAwayGoalDetail.toString().replace(";", "\n\n")
            yellowCardHome.text = data[0].strHomeYellowCards.toString().replace(";", "\n\n")
            yellowCardAway.text = data[0].strAwayYellowCards.toString().replace(";", "\n\n")
            redCardHome.text = data[0].strHomeRedCards.toString().replace(";", "\n\n")
            redCardAway.text = data[0].strAwayRedCard.toString().replace(";", "\n\n")
            formationHome.text = String.format(homeback.toString()) + " : " + String.format(homemid.toString()) +
                    " : " + String.format(homefront.toString())
            formationAway.text = String.format(awayback.toString()) + " : " + String.format(awaymid.toString()) +
                    " : " + String.format(awayfront.toString())
            homeGoalKeeper.text = data[0].strHomeLineupGoalkeeper.toString().replace(";", "\n\n")
            awayGoalKeeper.text = data[0].strAwayLineupGoalkeeper.toString().replace(";", "\n\n")
            homeDef.text = data[0].strHomeLineupDefense.toString().replace(";", "\n\n")
            awayDef.text = data[0].strAwayLineupDefense.toString().replace(";", "\n\n")
            homeMid.text = data[0].strHomeLineupMidfield.toString().replace(";", "\n\n")
            awayMid.text = data[0].strAwayLineupMidfield.toString().replace(";", "\n\n")
            homeFront.text = data[0].strHomeLineupForward.toString().replace(";", "\n\n")
            awayFront.text = data[0].strAwayLineupForward.toString().replace(";", "\n\n")
            homeSubs.text = data[0].strHomeLineupSubstitutes.toString().replace(";", "\n\n")
            awaySubs.text = data[0].strAwayLineupSubstitutes.toString().replace(";", "\n\n")
        }
    }

    override fun showTeamImageList(data: List<TeamImage>) {
        homeImage = TeamImage(data[0].teamBadge)
        swipeRefresh.isRefreshing = false
        Picasso.get().load(data[0].teamBadge).into(teamHomePict)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if(::matchEvent.isInitialized){
                    if(isFavorite) removeFromFavorite() else addToFavorites()
                    isFavorite = !isFavorite
                    setFavorite()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorites(){
        try{
            database.use{
                insert(
                    FavoritesMatch.TABLE_FAVORITE,
                    FavoritesMatch.MATCH_ID to idEvent,
                    FavoritesMatch.DATE to matchEvent.strDate,
                    FavoritesMatch.TIME to matchEvent.strTime,
                    FavoritesMatch.TEAM_HOME_NAME to matchEvent.strHomeTeam,
                    FavoritesMatch.TEAM_AWAY_NAME to matchEvent.strAwayTeam,
                    FavoritesMatch.TEAM_HOME_BADGE to homeImage.teamBadge,
                    FavoritesMatch.TEAM_AWAY_BADGE to awayImage.teamAwayBadge,
                    FavoritesMatch.TEAM_HOME_SCORE to matchEvent.intHomeScore,
                    FavoritesMatch.TEAM_AWAY_SCORE to matchEvent.intAwayScore,
                    FavoritesMatch.TEAM_HOME_SHOOT to matchEvent.intHomeShoot,
                    FavoritesMatch.TEAM_AWAY_SHOOT to matchEvent.intAwayShoot,
                    FavoritesMatch.TEAM_HOME_GOAL_DETAIL to matchEvent.strHomeGoalDetail,
                    FavoritesMatch.TEAM_AWAY_GOAL_DETAIL to matchEvent.strAwayGoalDetail,
                    FavoritesMatch.TEAM_HOME_GOAL_KEEPER to matchEvent.strHomeLineupGoalkeeper,
                    FavoritesMatch.TEAM_AWAY_GOAL_KEEPER to matchEvent.strAwayLineupGoalkeeper,
                    FavoritesMatch.TEAM_HOME_DEF to matchEvent.strHomeLineupDefense,
                    FavoritesMatch.TEAM_AWAY_DEF to matchEvent.strAwayLineupDefense,
                    FavoritesMatch.TEAM_HOME_MID to matchEvent.strHomeLineupMidfield,
                    FavoritesMatch.TEAM_AWAY_MID to matchEvent.strAwayLineupMidfield,
                    FavoritesMatch.TEAM_HOME_FRONT to matchEvent.strHomeLineupForward,
                    FavoritesMatch.TEAM_AWAY_FRONT to matchEvent.strAwayLineupForward,
                    FavoritesMatch.TEAM_HOME_SUBS to matchEvent.strHomeLineupSubstitutes,
                    FavoritesMatch.TEAM_AWAY_SUBS to matchEvent.strAwayLineupSubstitutes,
                    FavoritesMatch.TEAM_HOME_YELLOW_CARD to matchEvent.strHomeYellowCards,
                    FavoritesMatch.TEAM_AWAY_YELLOW_CARD to matchEvent.strAwayYellowCards,
                    FavoritesMatch.TEAM_AWAY_RED_CARD to matchEvent.strAwayRedCard,
                    FavoritesMatch.TEAM_HOME_RED_CARD to matchEvent.strHomeRedCards,
                    FavoritesMatch.TEAM_HOME_FORMATION to formationHome.toString(),
                    FavoritesMatch.TEAM_AWAY_FORMATION to formationAway.toString(),
                    FavoritesMatch.TEAM_HOME_ID to idHomeTeam,
                    FavoritesMatch.TEAM_AWAY_ID to idAwayTeam,
                    FavoritesMatch.SEASON to season)
            }
            swipeRefresh.snackbar("Added to Favorite").show()
        }catch(e: SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoritesMatch.TABLE_FAVORITE, "(MATCH_ID = {event})",
                    "event" to idEvent)
            }
            swipeRefresh.snackbar( "Removed from favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun setFavorite(){
        if(isFavorite){
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorite)
        }else{
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorite)
        }
    }
}