package com.example.vtb_hack.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.vtb_hack.R
import com.example.vtb_hack.data.DataBase
import com.example.vtb_hack.setPhoto
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.car_page.*
import java.text.NumberFormat

class CarPageFragment : Fragment(R.layout.car_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val format = NumberFormat.getInstance()

        var priceCar: Int? = null

        if (arguments?.getString("model") != null) {
            carPhoto.setPhoto(Uri.parse(arguments?.getString("bigPhoto")))

            title.text = getString(
                R.string.carName,
                arguments?.getString("brand"),
                arguments?.getString("model")
            )

            carName.text = getString(
                R.string.carName,
                arguments?.getString("brand"),
                arguments?.getString("model")
            )
            carPrice.text = getString(R.string.carPrice, format.format(arguments?.getInt("price")))
            priceCar = arguments?.getInt("price")
            countryValue.text = getString(R.string.countryValue, arguments?.getString("country"))
            bodyTypeValue.text = getString(R.string.bodyTypeValue, arguments?.getString("bodyType"))
            countDoorValue.text =
                getString(R.string.countDoorsValue, arguments?.getInt("doorsCount"))
            countColorsValue.text =
                getString(R.string.countColorsValue, arguments?.getInt("colorsCount"))


        } else {
            val id = arguments!!.getLong("id")
            val car = DataBase.getDataBase().carDAO().getById(id)

            car.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    carPhoto.setPhoto(it.bigPhoto)

                    title.text = getString(R.string.carName, it.brand, it.model)
                    carName.text = getString(R.string.carName, it.brand, it.model)
                    carPrice.text = getString(R.string.carPrice, format.format(it.price))
                    priceCar = it.price
                    countryValue.text = getString(R.string.countryValue, it.country)
                    bodyTypeValue.text = getString(R.string.bodyTypeValue, it.bodyType)
                    countDoorValue.text = getString(R.string.countDoorsValue, it.doorsCount)
                    countColorsValue.text = getString(R.string.countColorsValue, it.colorsCount)
                }
                .subscribe()
        }

        calculate.setOnClickListener {
            if (priceCar != null) {
                val intent = Intent(context, CreditRequest::class.java)
                intent.putExtra("priceCar", priceCar)
                intent.putExtra("nameCar", carName.text)
                startActivity(intent)
            }
        }
    }
}