package com.example.vtb_hack.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vtb_hack.R
import kotlinx.android.synthetic.main.filter_item.view.*

class FilterAdapter(private val filters: List<String>): RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val filterName: TextView = itemView.filterName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.filter_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.filterName.text = filters[position]
    }

    override fun getItemCount(): Int = filters.size
}