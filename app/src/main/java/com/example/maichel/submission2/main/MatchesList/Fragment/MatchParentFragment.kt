package com.example.maichel.submission2.main.MatchesList.MatchesActivity.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.example.maichel.submission2.R
import com.example.maichel.submission2.main.ViewPagerHelper.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_match_parent.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MatchParentFragment : Fragment() {

    private lateinit var editText: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_parent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<View>(R.id.viewPager_main) as ViewPager
        viewPager.adapter = ViewPagerAdapter(childFragmentManager)
        tabs_main.setupWithViewPager(viewPager_main)
    }
}
