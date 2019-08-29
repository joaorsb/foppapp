package com.foppal247.foppapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.EventLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foppal247.foppapp.R
import kotlinx.android.synthetic.main.fragment_favorites.*
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.activity.NewsListActivity
import com.foppal247.foppapp.adapter.FavoriteTeamsAdapter
import com.foppal247.foppapp.domain.FavoriteTeamsService
import com.foppal247.foppapp.domain.model.FavoriteTeam
import com.foppal247.foppapp.extensions.FavoriteEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread
import org.jetbrains.anko.yesButton


class FavoritesFragment: BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              icicle: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        return view

    }

    override fun onViewCreated(view: View, icicle: Bundle?) {
        super.onViewCreated(view, icicle)
        super.checkConnection()
        recyclerViewFavorites.layoutManager = LinearLayoutManager(activity)
        recyclerViewFavorites.itemAnimator = DefaultItemAnimator()
        recyclerViewFavorites.setHasFixedSize(true)



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(super.hasConnection){
            taskGetFavoriteTeams()
        }
    }

    @Subscribe
    fun onRefresh(event: FavoriteEvent){
        taskGetFavoriteTeams()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    private fun taskGetFavoriteTeams(){
        doAsync {
               FoppalApplication.getInstance().favoriteTeams = FavoriteTeamsService.getFavoriteTeams()
            uiThread {
                recyclerViewFavorites.adapter = FavoriteTeamsAdapter(FoppalApplication.getInstance().favoriteTeams) { onClickTeam(it)}
                setupSwipeToDelete()
            }
        }

    }

    private fun onClickTeam(team: FavoriteTeam) {
        FoppalApplication.getInstance().englishNews = false
        FoppalApplication.getInstance().newsList = mutableListOf()
        FoppalApplication.getInstance().selectedIntlTeamName = team.intlName
        FoppalApplication.getInstance().selectedTeamName = team.teamName
        FoppalApplication.getInstance().country = team.country
        startActivity<NewsListActivity>("leagueType" to FoppalApplication.getInstance().league)
    }

    private fun setupSwipeToDelete() {
        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                alert("Deseja excluir dos favoritos?", "Excluir") {
                    yesButton {
                        (recyclerViewFavorites.adapter as FavoriteTeamsAdapter).removeItem(viewHolder)
                        toast("Time excluído com sucesso")
                    }
                    noButton {
                        recyclerViewFavorites.adapter?.notifyDataSetChanged()
                        toast("Ação cancelada")
                    }
                }.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewFavorites)
    }
}