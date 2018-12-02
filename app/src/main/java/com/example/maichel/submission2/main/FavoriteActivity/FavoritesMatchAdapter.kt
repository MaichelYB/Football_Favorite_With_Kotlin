package com.example.maichel.submission2.main.FavoriteActivity

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.maichel.submission2.R
import com.example.maichel.submission2.database.FavoritesMatch
import org.jetbrains.anko.*

class FavoritesMatchAdapter(private val favorite: List<FavoritesMatch>, private val listen:(FavoritesMatch) -> Unit)
    :RecyclerView.Adapter<FavoritesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            FavoritesTeamUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
    }

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bindItem(favorite[position], listen)
    }

}
class FavoritesTeamUI: AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(5)
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_HORIZONTAL

                textView{
                    id = R.id.date_match
                    textSize = 16f
                }.lparams {
                    margin = dip(15)
                }

                linearLayout {
                    id = R.id.linear_matches
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(3)
                    orientation = LinearLayout.HORIZONTAL
                    weightSum = 3F
                    textView {
                        id = R.id.team_home_name
                        textSize = 16f
                        gravity = Gravity.START
                        ellipsize = TextUtils.TruncateAt.END
                        singleLine = true
                        width = 0
                        setPadding(5, 5, dip(15), 5)
                    }.lparams {
                        topMargin = dip(10)
                        weight = 1.35F
                    }
                    textView {
                        id = R.id.team_home_score
                        textSize = 16f
                    }.lparams {
                        topMargin = dip(10)
                        weight = 0.1F
                    }
                    textView {
                        id = R.id.separator
                        textSize = 16f
                    }.lparams {
                        topMargin = dip(10)
                        weight = 0.1F
                    }
                    textView {
                        id = R.id.team_away_score
                        textSize = 16f
                    }.lparams {
                        topMargin = dip(10)
                        weight = 0.1F
                    }
                    textView {
                        id = R.id.team_away_name
                        textSize = 16f
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
            }
        }
    }

}
class FavoritesViewHolder(view: View): RecyclerView.ViewHolder(view){
    private val teamHomeName: TextView = view.find(R.id.team_home_name)
    private val teamHomeScore: TextView = view.find(R.id.team_home_score)
    private val teamAwayName: TextView = view.find(R.id.team_away_name)
    private val teamAwayScore: TextView = view.find(R.id.team_away_score)
    private val dateMatch: TextView = view.find(R.id.date_match)
    private val separator: TextView = view.find(R.id.separator)
    private var teamAwayPict: String? = null
    private var teamHomePict: String? = null
    private var time: String? = null
    private var teamHomeShoot: String? = null
    private var teamAwayShoot: String? = null
    private var goalDetailHome: String? = null
    private var goalDetailAway: String? = null
    private var yellowCardHome: String? = null
    private var yellowCardAway: String? = null
    private var redCardHome: String? = null
    private var redCardAway: String? = null
    private var formationHome: String? = null
    private var formationAway: String? = null
    private var homeGoalKeeper: String? = null
    private var awayGoalKeeper: String? = null
    private var homeDef: String? = null
    private var awayDef: String? = null
    private var homeMid: String? = null
    private var awayMid: String? = null
    private var homeFront: String? = null
    private var awayFront: String? = null
    private var homeSubs: String? = null
    private var awaySubs: String? = null

    fun bindItem(favorite: FavoritesMatch, listen: (FavoritesMatch) -> Unit){
        dateMatch.text = favorite.teamHomeName
        teamHomeName.text =favorite.DateMatch
        teamAwayName.text = favorite.TimeMatch
        teamHomeScore.text = favorite.TeamHomeScore
        teamAwayScore.text = favorite.TeamAwayScore
        teamAwayPict = favorite.teamAwayBadge
        teamHomePict = favorite.teamHomeBadge
        time = favorite.TimeMatch
        teamHomeShoot = favorite.HomeTotalShoot
        teamAwayShoot = favorite.AwayTotalShoot
        goalDetailHome = favorite.HomeGoalDetail
        goalDetailAway = favorite.AwayGoalDetail
        yellowCardHome = favorite.HomeYellowCards
        yellowCardAway = favorite.AwayYellowCards
        redCardHome = favorite.HomeRedCards
        redCardAway = favorite.AwayRedCards
        formationHome = favorite.TeamHomeFormation
        formationAway = favorite.TeamAwayFormation
        homeGoalKeeper = favorite.HomeGoalKeeper
        awayGoalKeeper = favorite.AwayGoalKeeper
        homeDef = favorite.HomeDef
        awayDef = favorite.AwayDef
        homeMid = favorite.HomeMid
        awayMid = favorite.AwayMid
        homeFront = favorite.HomeFront
        awayFront = favorite.AwayFront
        homeSubs = favorite.HomeSubtitutes
        awaySubs = favorite.AwaySubtitutes

        if(favorite.TeamHomeScore == null){
            separator.textResource = R.string.versus
        }else{
            separator.textResource = R.string.separator
        }

        itemView.setOnClickListener { listen(favorite) }
    }
}