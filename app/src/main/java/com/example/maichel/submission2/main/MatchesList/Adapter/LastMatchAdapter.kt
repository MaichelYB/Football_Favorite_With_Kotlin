package com.example.maichel.submission2.main.MatchesList.MatchesActivity.Adapter

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.maichel.submission2.R
import com.example.maichel.submission2.R.id.*
import com.example.maichel.submission2.model.MatchEvent
import com.example.maichel.submission2.util.bold
import com.example.maichel.submission2.util.formatDate
import org.jetbrains.anko.*

class LastMatchAdapter(private val n15MatchEvents: List<MatchEvent>, private val listen: (MatchEvent) -> Unit) :
    RecyclerView.Adapter<LastMatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastMatchViewHolder {
        //val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return LastMatchViewHolder(
            LastMatchUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
        //return NextMatchViewHolder(v)
    }

    override fun getItemCount(): Int = n15MatchEvents.size

    override fun onBindViewHolder(holderMatchViewHolder: LastMatchViewHolder, position: Int) {
        holderMatchViewHolder.bindItem(n15MatchEvents[position], listen)
    }

}

class LastMatchUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_HORIZONTAL
                weightSum = 3F
                //bottomPadding = dip(50)

                textView {
                    id = date_match
                    textSize = 16f
                }.lparams {
                    margin = dip(15)
                }

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(3)
                    orientation = LinearLayout.HORIZONTAL
                    weightSum = 3F
                    textView {
                        id = team_home_name
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
                        id = team_home_score
                        textSize = 16f
                    }.lparams {
                        topMargin = dip(10)
                        weight = 0.1F
                    }
                    textView {
                        textResource = R.string.separator
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
                        id = team_away_name
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

class LastMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val teamHomeName: TextView = view.find(R.id.team_home_name)
    private val teamHomeScore: TextView = view.find(R.id.team_home_score)
    private val teamAwayName: TextView = view.find(R.id.team_away_name)
    private val teamAwayScore: TextView = view.find(R.id.team_away_score)
    private val dateMatch: TextView = view.find(R.id.date_match)
    fun bindItem(teams: MatchEvent, listen: (MatchEvent) -> Unit) {
        dateMatch.text = teams.strDate?.formatDate()

        teamHomeName.text = teams.strHomeTeam
        teamHomeScore.text = teams.intHomeScore
        teamAwayName.text = teams.strAwayTeam
        teamAwayScore.text = teams.intAwayScore

        val scoreH = teams.intHomeScore?.toInt() ?: 0
        val scoreA = teams.intAwayScore?.toInt() ?: 0

        if (scoreH - scoreA > 0) {
            teamHomeName.bold()
            teamHomeScore.bold()
        }

        if (scoreH - scoreA < 0) {
            teamAwayName.bold()
            teamAwayScore.bold()
        }

        itemView.setOnClickListener {
            listen(teams)
        }
    }
}