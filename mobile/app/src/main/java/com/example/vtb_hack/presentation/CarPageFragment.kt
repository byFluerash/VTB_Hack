package com.example.vtb_hack.presentation

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.vtb_hack.R
import com.example.vtb_hack.data.Car
import com.example.vtb_hack.setPhoto
import kotlinx.android.synthetic.main.car_page.*

class CarPageFragment : Fragment(R.layout.car_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val car = Car(
            1,
            "LADA",
            "Granta",
            "Россия",
            273310,
            "Седан",
            3,
            3,
            Uri.parse("https://tradeins.space/uploads/photo/511795/hetch.png")
        )

        carPhoto.setPhoto(car.photo)

        title.text = getString(R.string.carName, car.brand, car.model)
        carName.text = getString(R.string.carName, car.brand, car.model)
        carPrice.text = getString(R.string.carPrice, car.price)
        countryValue.text = getString(R.string.countryValue, car.country)
        bodyTypeValue.text = getString(R.string.bodyTypeValue, car.bodyType)
        countDoorValue.text =  getString(R.string.countDoorsValue, car.doorsCount)
        countColorsValue.text =  getString(R.string.countColorsValue, car.colorsCount)
    }
}