package com.example.maichel.submission2.main.MatchesList.MatchesActivity.Fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*

import com.example.maichel.submission2.R
import com.example.maichel.submission2.Event.SearchViewEvent
import com.example.maichel.submission2.api.ApiRepository
import com.example.maichel.submission2.main.MatchesList.MatchesActivity.Adapter.NextMatchAdapter
import com.example.maichel.submission2.main.Main.MainPresenter
import com.example.maichel.submission2.main.Main.MainView
import com.example.maichel.submission2.main.MatchesList.MatchDetail.Activity.MatchDetailActivity
import com.example.maichel.submission2.model.MatchEvent
import com.example.maichel.submission2.util.invisible
import com.example.maichel.submission2.util.visible
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class NextMatchFragment : Fragment(), AnkoComponent<Context>, MainView {
    private var teams: MutableList<MatchEvent> = mutableListOf()
    private var name = ArrayList<String>()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: NextMatchAdapter
    private lateinit var spinner: Spinner
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var editText: EditText
    private var access: String = ""
    private var alamat: String = ""
    private var query: String = ""
    private var isSearch: Boolean = false

    companion object {
        fun newInstance(): NextMatchFragment {
            return NextMatchFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val spinnerItems = resources.getStringArray(R.array.match)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        adapter = NextMatchAdapter(teams) {
            context?.startActivity<MatchDetailActivity>(
                "name_home_team" to "${it.strHomeTeam}",
                "season" to "${it.idSeason}",
                "name_away_team" to "${it.strAwayTeam}",
                "id_home_team" to "${it.idHomeTeam}",
                "id_away_team" to "${it.idAwayTeam}",
                "id_event" to "${it.idEvent}"
            )
        }

        listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                access = spinner.selectedItem.toString()

                if(access.equals("English Premiere League")){
                    alamat = "4328"
                }
                else if(access.equals("Italian Serie")){
                    alamat = "4332"
                }
                else if(access.equals("Brazilian Brasileirao")){
                    alamat = "4351"
                }
                else if(access.equals("Scottish Championship")){
                    alamat = "4395"
                }
                else{
                    alamat = "4401"
                }

                presenter.getn15Match(alamat)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        swipeRefresh.onRefresh {
            presenter.getn15Match(alamat)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner {
                id = R.id.spinner
            }
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)
                    listTeam = recyclerView {
                        id = R.id.list_next_match
                        lparams(width = matchParent, height = wrapContent) {
                            bottomPadding = dip(0)
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
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showMatchList(data: List<MatchEvent>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: SearchViewEvent) {
//        lakuin api call
        query = event.query
        if (!query.isNullOrBlank()) {
            isSearch = true
            presenter.getSearchEvent(query)
        }
    }

}