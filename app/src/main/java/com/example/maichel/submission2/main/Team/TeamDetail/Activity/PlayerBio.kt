package com.example.maichel.submission2.main.Team.TeamDetail.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ScrollView
import com.example.maichel.submission2.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class PlayerBio: AppCompatActivity() {
    private var name: String = ""
    private var thumb: String = ""
    private var heightPlayer: String = ""
    private var weightPlayer: String = ""
    private var desc: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        name = intent.getStringExtra("player_name")
        thumb = intent.getStringExtra("player_pict")
        heightPlayer = intent.getStringExtra("player_height")
        weightPlayer = intent.getStringExtra("player_weight")
        desc = intent.getStringExtra("player_desc")

        supportActionBar?.title = name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        scrollView {
            isVerticalScrollBarEnabled = false
            linearLayout {
                lparams(width = matchParent, height = matchParent)
                gravity = Gravity.CENTER_HORIZONTAL
                orientation = LinearLayout.VERTICAL

                imageView{
                    Picasso.get().load(thumb).into(this)
                }.lparams(width = matchParent, height = wrapContent){
                    height = dip(200)
                    width = matchParent
                }

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(3)
                    orientation = LinearLayout.HORIZONTAL
                    weightSum = 3F

                    textView{
                        text = "Height: ${heightPlayer}"
                        textSize = 16f
                        singleLine = true
                        width = 0
                        setPadding(5, 5, dip(15), 5)
                    }.lparams {
                        topMargin = dip(10)
                        weight = 1.35F
                    }

                    textView {
                        textResource = R.string.separator
                        textSize = 16f
                    }.lparams {
                        topMargin = dip(10)
                        weight = 0.1F
                    }

                    textView{
                        text = "Weight: ${weightPlayer}"
                        gravity = Gravity.END
                        textSize = 16f
                        singleLine = true
                        width = 0
                        setPadding(5, 5, dip(15), 5)
                    }.lparams {
                        topMargin = dip(10)
                        weight = 1.35F
                    }
                }

                linearLayout {
                    textView {
                        text = desc
                        textSize = 13f
                    }.lparams{

                    }
                }
            }
        }
    }
}