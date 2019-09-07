package com.foppal247.foppapp.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.foppal247.foppapp.R
import com.foppal247.foppapp.domain.FavoriteTeamsService
import com.foppal247.foppapp.domain.model.FavoriteTeam
import kotlinx.android.synthetic.main.adapter_favoriteteams.view.*

class FavoriteTeamsAdapter(
    private var favoriteTeams: MutableList<FavoriteTeam>,
    val onClick: (FavoriteTeam) -> Unit
) : RecyclerView.Adapter<FavoriteTeamsAdapter.FavoriteTeamsViewHolder> () {

    override fun getItemCount() = this.favoriteTeams.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_favoriteteams, parent, false)
        return FavoriteTeamsViewHolder(view)
    }

    fun setTeams(data: MutableList<FavoriteTeam>){
        favoriteTeams = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FavoriteTeamsViewHolder, position: Int) {
        val favorite = favoriteTeams[position]
        val view = holder.itemView
        with(view) {
            tFavoriteTeamName.text = favorite.teamName
        }
        holder.itemView.setOnClickListener { onClick(favorite)}
    }

    fun removeItem(holder: RecyclerView.ViewHolder){
        val position = holder.adapterPosition
        val favoriteTeam = favoriteTeams[position]
        FavoriteTeamsService.deleteFavorite(favoriteTeam)
        favoriteTeams.removeAt(holder.adapterPosition)
        notifyItemRemoved(holder.adapterPosition)
    }

    class FavoriteTeamsViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

