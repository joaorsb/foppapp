package com.foppal247.foppapp.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.foppal247.foppapp.R
import com.foppal247.foppapp.domain.FavoriteTeamsService
import com.foppal247.foppapp.domain.model.FavoriteTeam
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton

class FavoriteTeamsAdapter(
    var favoriteTeams: MutableList<FavoriteTeam>,
    val onClick: (FavoriteTeam) -> Unit
) : RecyclerView.Adapter<FavoriteTeamsAdapter.FavoriteTeamsViewHolder> () {
    class FavoriteTeamsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tFavoriteTeamName : TextView
        init {
            tFavoriteTeamName = view.findViewById(R.id.tFavoriteTeamName)
        }
    }
    override fun getItemCount() = this.favoriteTeams.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_favoriteteams, parent, false)
        val holder = FavoriteTeamsViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: FavoriteTeamsViewHolder, position: Int) {
        val favorite = favoriteTeams[position]
        holder.tFavoriteTeamName.text = favorite.teamName

        holder.itemView.setOnClickListener { onClick(favorite)}
    }

    fun removeItem(holder: RecyclerView.ViewHolder){
        val position = holder.adapterPosition
        val favoriteTeam = favoriteTeams[position]
        FavoriteTeamsService.deleteFavorite(favoriteTeam)
        favoriteTeams.removeAt(holder.adapterPosition)
        notifyItemRemoved(holder.adapterPosition)
    }
}

