package com.example.maichel.submission2.main.Team.TeamDetail.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.maichel.submission2.R
import com.example.maichel.submission2.model.TeamPlayerModel
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class PlayerAdapter(private val player: List<TeamPlayerModel>, private val listen: (TeamPlayerModel) -> Unit) :
    RecyclerView.Adapter<TeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        //val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return TeamViewHolder(
            TeamUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
        //return NextMatchViewHolder(v)
    }

    override fun getItemCount(): Int = player.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(player[position], listen)
    }
}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                lparams(width = matchParent, height = wrapContent)
                padding = dip(3)
                orientation = LinearLayout.HORIZONTAL
                weightSum = 3F

                linearLayout {
                    imageView {
                        id = R.id.player_image
                    }.lparams {
                        height = dip(50)
                        width = dip(50)
                        weight = 1.35F
                    }
                }

                linearLayout {
                    textView{
                        id = R.id.player_name
                        textSize = 13f
                    }.lparams{
                        weight = 0.1F
                    }
                }

                linearLayout {
                    textView{
                        id = R.id.player_pos
                        textSize = 13f
                    }.lparams {
                        weight = 1.35F
                    }
                }
            }
        }
    }
}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val playerImage: ImageView = view.find(R.id.player_image)
    private val playerName: TextView = view.find(R.id.player_name)
    private val playerPos: TextView = view.find(R.id.player_pos)

    fun bindItem(player: TeamPlayerModel, listen: (TeamPlayerModel) -> Unit) {
        if(player.strCutout!= null){
            Picasso.get().load(player.strCutout).into(playerImage)
        }
        else{
            Picasso.get().load(player.strThumb).into(playerImage)
        }
        playerName.text = player.strName
        playerPos.text = "(${player.strPos})"
        itemView.setOnClickListener {
            listen(player)
        }
    }
}