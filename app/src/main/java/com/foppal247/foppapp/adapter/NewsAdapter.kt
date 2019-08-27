package com.foppal247.foppapp.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.foppal247.foppapp.R
import com.foppal247.foppapp.domain.model.News
import com.foppal247.foppapp.FoppalApplication
import kotlinx.android.synthetic.main.adapter_news.view.*

class NewsAdapter (
    var news: MutableList<News>,
    val onClick: (News) -> Unit
    ) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder> () {

    override fun getItemCount() = this.news.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_news, parent, false)
        val holder = NewsViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = news[position]
        val view = holder.itemView
        val arrayDate = newsItem.publishingDate.split('-', 'T')
        val arrayHour = arrayDate[3].split(":")
        val dateString: String

        with(view){
            if(FoppalApplication.getInstance().country != "brazil") {
                dateString = "${arrayDate[2]}/${arrayDate[1]}/${arrayDate[0]} ${arrayHour[0]}:${arrayHour[1]}"
            } else {
                dateString = "${arrayDate[2]}/${arrayDate[1]}/${arrayDate[0]}"
            }
            tHeadline.text = newsItem.headline
            tSourceUrl.text = newsItem.sourceUrl
            tPublishingDate.text = dateString

            setOnClickListener { onClick(newsItem)}
        }

    }

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }





}