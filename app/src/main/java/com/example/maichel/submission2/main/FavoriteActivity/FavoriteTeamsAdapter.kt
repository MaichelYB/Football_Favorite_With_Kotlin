package com.example.maichel.submission2.main.FavoriteActivity

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.maichel.submission2.R
import com.example.maichel.submission2.database.FavoritesTeam
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class FavoriteTeamsAdapter(private val favorites: List<FavoritesTeam>, private val listen:(FavoritesTeam) -> Unit)
    : RecyclerView.Adapter<FavoritesTeamHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FavoritesTeamHolder {
        return FavoritesTeamHolder(
            FavoriteTeamUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
    }

    override fun getItemCount(): Int = favorites.size

    override fun onBindViewHolder(holder: FavoritesTeamHolder, position: Int) {
        holder.bindItem(favorites[position], listen)
    }
}

class FavoriteTeamUI: AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.team_badge
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.team_name
                    textSize = 16f
                }.lparams{
                    margin = dip(15)
                }
            }
        }
    }
}

class FavoritesTeamHolder(view: View): RecyclerView.ViewHolder(view){
    private var teamBadge: ImageView = view.findViewById(R.id.team_badge)
    private var teamName: TextView = view.findViewById(R.id.team_name)

    fun bindItem(favorites: FavoritesTeam, listen: (FavoritesTeam) -> Unit){

        Picasso.get().load(favorites.teamBadge).into(teamBadge)
        teamName.text = favorites.teamName

        itemView.setOnClickListener {
            listen(favorites)
        }
    }
}