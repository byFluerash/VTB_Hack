package com.example.vtb_hack.presentation

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.vtb_hack.R
import com.example.vtb_hack.data.Car
import kotlinx.android.synthetic.main.main_view.*

class MainView : Fragment(R.layout.main_view) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cars = listOf(
            Car(
                1,
                "LADA",
                "Granta",
                "Россия",
                273310,
                3,
                Uri.parse("https://tradeins.space/uploads/photo/511795/hetch.png")
            ),

            Car(
                2,
                "Toyota",
                "Camry",
                "Россия",
                273310,
                3,
                Uri.parse("https://tradeins.space/uploads/photo/511795/hetch.png")
            ),

            Car(
                3,
                "BMW",
                "X5",
                "Россия",
                273310,
                3,
                Uri.parse("https://tradeins.space/uploads/photo/511795/hetch.png")
            )
        )

        carRecycler.adapter = CarAdapter(cars)
        val filters = listOf("Марка", "Тип кузова", "Цвет", "5000")
        filterRecycler.adapter = FilterAdapter(filters)
    }
}