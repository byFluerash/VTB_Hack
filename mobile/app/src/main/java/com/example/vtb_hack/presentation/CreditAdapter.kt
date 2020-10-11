package com.example.vtb_hack.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vtb_hack.R
import com.example.vtb_hack.data.Credit
import kotlinx.android.synthetic.main.credit_item.view.*

class CreditAdapter(var credit: List<Credit>) : RecyclerView.Adapter<CreditAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val carName: TextView = itemView.carName
        val state: TextView = itemView.state
        val sum: TextView = itemView.sum
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.credit_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.carName.text = credit[position].carName
        holder.state.text = credit[position].state
        holder.sum.text = credit[position].sum.toString()
    }

    override fun getItemCount() = credit.size
}