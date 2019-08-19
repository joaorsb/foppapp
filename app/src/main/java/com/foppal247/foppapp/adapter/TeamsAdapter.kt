package com.foppal247.foppapp.adapter


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.foppal247.foppapp.R
import com.foppal247.foppapp.domain.model.Team

class TeamsAdapter (
    val teams: List<Team>,
    val onClick: (Team) -> Unit) :
    RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder> () {
    class TeamsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tTeamName : TextView
        init {
            tTeamName = view.findViewById(R.id.tTeamName)
        }
    }
    override fun getItemCount() = this.teams.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_teams, parent, false)
        val holder = TeamsViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        val team = teams[position]
        holder.tTeamName.text = team.teamName

        holder.itemView.setOnClickListener { onClick(team)}
    }
}