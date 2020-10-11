package com.example.vtb_hack.presentation

import android.app.Activity
import android.graphics.Color
import android.icu.text.NumberFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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

    val format = NumberFormat.getInstance()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.carName.text = credit[position].carName
        holder.state.text = credit[position].state
        if (credit[position].state == "Отказано") {
            holder.state.setTextColor(Color.parseColor("#EB5757"))
        } else {
            holder.state.setTextColor(Color.parseColor("#4CBEA3"))
        }
        holder.sum.text = (holder.itemView.context as Activity).getString(R.string.everyMonthPrice,  format.format(credit[position].sum))
    }

    override fun getItemCount() = credit.size
}