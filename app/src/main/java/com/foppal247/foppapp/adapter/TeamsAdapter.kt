package com.foppal247.foppapp.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.foppal247.foppapp.R
import com.foppal247.foppapp.domain.model.FootballTeam
import com.foppal247.foppapp.domain.model.News
import kotlinx.android.synthetic.main.adapter_teams.view.*

class TeamsAdapter (
    var teams: MutableList<FootballTeam>,
    val onClick: (FootballTeam) -> Unit) :
    RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder> () {

    fun setTeamsData(data: MutableList<FootballTeam>){
        teams = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = this.teams.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_teams, parent, false)
        val holder = TeamsViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        val team = teams[position]
        val view = holder.itemView
        with(view){
            tFootballTeamName.text = team.teamName
        }

        holder.itemView.setOnClickListener { onClick(team)}
    }

    class TeamsViewHolder(view: View) : RecyclerView.ViewHolder(view)
}