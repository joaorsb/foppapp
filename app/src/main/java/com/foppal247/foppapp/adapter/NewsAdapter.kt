package com.foppal247.foppapp.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.foppal247.foppapp.R
import com.foppal247.foppapp.domain.model.News
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.domain.NewsService
import org.jetbrains.anko.doAsync


class NewsAdapter (
    val news: MutableList<News>,
    val onClick: (News) -> Unit
    ) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder> () {
    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tHeadline : TextView
        var tSourceUrl : TextView
        var tPublishingDate : TextView
        init {
            tHeadline = view.findViewById(R.id.tHeadline)
            tSourceUrl = view.findViewById(R.id.tSourceUrl)
            tPublishingDate = view.findViewById(R.id.tPublishingDate)
        }
    }
    override fun getItemCount() = this.news.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_news, parent, false)
        val holder = NewsViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = news[position]
        val arrayDate = news.publishingDate.split('-', 'T')
        val arrayHour = arrayDate[3].split(":")
        val dateString: String
        if(FoppalApplication.getInstance().country != "brazil") {
            dateString = "${arrayDate[2]}/${arrayDate[1]}/${arrayDate[0]} ${arrayHour[0]}:${arrayHour[1]}"
        } else {
            dateString = "${arrayDate[2]}/${arrayDate[1]}/${arrayDate[0]}"
        }
        holder.tHeadline.text = news.headline
        holder.tSourceUrl.text = news.sourceUrl
        holder.tPublishingDate.text = dateString

        holder.itemView.setOnClickListener { onClick(news)}
    }

}