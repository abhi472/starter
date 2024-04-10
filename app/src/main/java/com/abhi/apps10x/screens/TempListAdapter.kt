package com.abhi.apps10x.screens;

import android.view.LayoutInflater;
import android.view.View
import android.view.ViewGroup;
import android.widget.TextView

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView;

import com.abhi.apps10x.R;
import com.skydoves.powerspinner.createPowerSpinnerView

class TempListAdapter: RecyclerView.Adapter<TempListAdapter.ViewHolder>() {


     var articles: List<Pair<String, String>> = emptyList();



    fun setNews(article: List<Pair<String, String>> ) {
        this.articles =article;
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val dayText: TextView = itemView.findViewById(R.id.title)
        val degreeText: TextView = itemView.findViewById(R.id.dateTime)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val binding = LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = articles[position]
        holder.dayText.text = dataItem.first
        holder.degreeText.text = dataItem.second
    }


}


