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
import com.example.maichel.submission2.util.formatDate
import org.jetbrains.anko.*

class NextMatchAdapter(private val n15MatchEvents: List<MatchEvent>, private val listen: (MatchEvent) -> Unit) :
    RecyclerView.Adapter<NextMatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextMatchViewHolder {
        //val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return NextMatchViewHolder(
            NextMatchUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
        //return NextMatchViewHolder(v)
    }

    override fun getItemCount(): Int = n15MatchEvents.size

    override fun onBindViewHolder(holder: NextMatchViewHolder, position: Int) {
        holder.bindItem(n15MatchEvents[position], listen)
    }
}

class NextMatchUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_HORIZONTAL

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
                        textResource = R.string.versus
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
                    }.lparams {
                        topMargin = dip(10)
                        weight = 1.35F
                    }
                }
            }
        }
    }
}

class NextMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val teamHomeName: TextView = view.find(R.id.team_home_name)
    private val teamAwayName: TextView = view.find(R.id.team_away_name)
    private val dateMatch: TextView = view.find(R.id.date_match)

    fun bindItem(teams: MatchEvent, listen: (MatchEvent) -> Unit) {
        dateMatch.text = teams.strDate?.formatDate()
        teamHomeName.text = teams.strHomeTeam
        teamAwayName.text = teams.strAwayTeam
        itemView.setOnClickListener {
            listen(teams)
        }
    }
}