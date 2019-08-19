package com.foppal247.foppapp.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.foppal247.foppapp.R
import com.foppal247.foppapp.domain.model.Country


class CountryAdapter (
    val countries: List<Country>,
    val onClick: (Country) -> Unit) :
    RecyclerView.Adapter<CountryAdapter.CountriesViewHolder> () {
    class CountriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tCountryName : TextView
        var imgCountryFlag : ImageView
        init {
            tCountryName = view.findViewById(R.id.tCountryName)
            imgCountryFlag = view.findViewById(R.id.imgCountryFlag)
        }
    }
    override fun getItemCount() = this.countries.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_country, parent, false)
        val holder = CountriesViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val country = countries[position]
        holder.tCountryName.text = country.countryName
        holder.imgCountryFlag.setImageResource(country.flagUrl)
        holder.imgCountryFlag.contentDescription = country.countryName

        holder.itemView.setOnClickListener { onClick(country)}
    }
}