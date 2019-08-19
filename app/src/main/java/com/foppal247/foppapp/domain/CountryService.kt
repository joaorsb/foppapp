package com.foppal247.foppapp.domain

import android.content.Context
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.R
import com.foppal247.foppapp.domain.model.Country

object CountryService {
    fun getCountries(context: Context?): List<Country> {
        val countries = mutableListOf<Country>()
        var norge =
            Country(context?.getString(R.string.norge), R.mipmap.ic_norge_flag)
        var brasil =
            Country(context?.getString(R.string.brasil), R.mipmap.ic_brazil_flag)
        var espana =
            Country(context?.getString(R.string.espana), R.mipmap.ic_espana_flag)
        var sverige =
            Country(context?.getString(R.string.sverige), R.mipmap.ic_sverige_flag)
        var deutschland = Country(
            context?.getString(R.string.deutschland),
            R.mipmap.ic_deutschland_flag
        )
        var nederland = Country(
            context?.getString(R.string.nederland),
            R.mipmap.ic_nederland_flag
        )

        countries.addAll(listOf(norge, brasil, espana, sverige, deutschland, nederland))

        return countries
    }

    fun getAppCountries (context: Context?): String {
        var country = "norge"
        when(FoppalApplication.getInstance().country) {
            context?.getString(R.string.norge) -> { country = "norge" }
            context?.getString(R.string.brasil) -> { country = "brazil" }
            context?.getString(R.string.espana) -> { country = "espana" }
            context?.getString(R.string.sverige) -> { country = "sverige" }
            context?.getString(R.string.nederland) -> { country = "nederland" }
            context?.getString(R.string.deutschland) -> { country = "deutschland" }
        }
        return country
    }
}