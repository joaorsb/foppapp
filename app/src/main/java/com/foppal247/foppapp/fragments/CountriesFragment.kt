package com.foppal247.foppapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.R
import kotlinx.android.synthetic.main.fragment_countries.*
import com.foppal247.foppapp.adapter.CountryAdapter
import com.foppal247.foppapp.domain.CountryService
import com.foppal247.foppapp.domain.model.Country
import com.google.android.material.navigation.NavigationView


class CountriesFragment: BaseFragment() {
    private var countriesList = listOf<Country>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              icicle: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_countries, container, false)
        return view
    }

    override fun onViewCreated(view: View, icicle: Bundle?) {
        super.onViewCreated(view, icicle)
        recyclerViewCountries.layoutManager = LinearLayoutManager(activity)
        recyclerViewCountries.itemAnimator = DefaultItemAnimator()
        recyclerViewCountries.setHasFixedSize(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        taskCountries()
    }

    private fun taskCountries(){
        countriesList = CountryService.getCountries(context)
        recyclerViewCountries.adapter = CountryAdapter(countriesList) { onClickCountry(it) }
    }

    private fun onClickCountry(country: Country) {
        val navView = activity?.findViewById<NavigationView>(R.id.nav_view)
        FoppalApplication.getInstance().country = country.countryAPIName as String
        FoppalApplication.getInstance().menuGroupId = country.menuGroupId
        navView?.menu?.forEach { item: MenuItem ->  item.isVisible = false}

        navView?.menu?.forEach { item: MenuItem ->
            if (item.groupId == FoppalApplication.getInstance().menuGroupId) {
                navView.menu?.setGroupVisible(FoppalApplication.getInstance().menuGroupId, true)
            }
        }

        val drawer = activity?.findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer?.openDrawer(GravityCompat.START)
    }

}