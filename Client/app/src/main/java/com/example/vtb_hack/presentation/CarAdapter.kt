package com.example.vtb_hack.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vtb_hack.R
import com.example.vtb_hack.data.Car
import com.example.vtb_hack.data.CarDB
import com.example.vtb_hack.setPhoto
import kotlinx.android.synthetic.main.car_item.*
import kotlinx.android.synthetic.main.car_item.view.*
import java.text.NumberFormat

class CarAdapter(private val cars: List<CarDB>) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carName: TextView = itemView.carName
        val carPrice: TextView = itemView.carPrice
        val carPhoto: ImageView = itemView.carPhoto
        val openPage: ImageButton = itemView.openPage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return ViewHolder(itemView)
    }

    private val format = NumberFormat.getInstance()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.carName.text = holder.itemView.context.getString(R.string.carName, cars[position].brand, cars[position].model)
        holder.carPrice.text = holder.itemView.context.getString(R.string.carPrice, format.format(cars[position].price))
        holder.carPhoto.setPhoto(cars[position].photo)

        holder.openPage.setOnClickListener {
            val fragment = CarPageFragment()
            fragment.arguments = Bundle().apply {
                putLong("id", cars[position].id)
            }

            (it.context as MainActivity).supportFragmentManager.beginTransaction().addToBackStack("carList").replace(R.id.mainFragment, fragment).commit()
        }
    }

    override fun getItemCount(): Int = cars.size
}