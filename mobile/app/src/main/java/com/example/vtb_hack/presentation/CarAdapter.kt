package com.example.vtb_hack.presentation

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
import com.example.vtb_hack.setPhoto
import kotlinx.android.synthetic.main.car_item.*
import kotlinx.android.synthetic.main.car_item.view.*

class CarAdapter(private val cars: List<Car>) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.carName.text = holder.itemView.context.getString(R.string.carName, cars[position].brand, cars[position].model)
        holder.carPrice.text = holder.itemView.context.getString(R.string.carPrice, cars[position].price)
        holder.carPhoto.setPhoto(cars[position].photo)

        holder.openPage.setOnClickListener {
            (it.context as MainActivity).supportFragmentManager.beginTransaction().addToBackStack("carList").replace(R.id.mainFragment, CarPageFragment()).commit()
        }
    }

    override fun getItemCount(): Int = cars.size
}