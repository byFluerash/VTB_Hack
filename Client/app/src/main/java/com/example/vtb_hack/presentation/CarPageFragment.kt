package com.example.vtb_hack.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vtb_hack.R
import com.example.vtb_hack.data.Content
import com.example.vtb_hack.data.DataBase
import com.example.vtb_hack.data.RetrofitInstance
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
        content.visibility = View.INVISIBLE

        progressBar.visibility = View.VISIBLE

        if (arguments?.getString("encodedImage") != null) {

            val res = RetrofitInstance.instance.postCar(Content(arguments?.getString("encodedImage")!!))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        carPhoto.setPhoto(it.bigPhoto)

                        title.text = getString(
                            R.string.carName,
                            it.brand,
                            it.model
                        )

                        carName.text = getString(
                            R.string.carName,
                            it.brand,
                            it.model
                        )
                        carPrice.text = getString(R.string.carPrice, format.format(it.price))
                        priceCar = it.price
                        countryValue.text = getString(R.string.countryValue, it.country)
                        bodyTypeValue.text = getString(R.string.bodyTypeValue, it.bodyType)
                        countDoorValue.text =
                            getString(R.string.countDoorsValue, it.doorsCount)
                        countColorsValue.text =
                            getString(R.string.countColorsValue, it.colorsCount)
                        content.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                    },
                    {
                        Toast.makeText(context, "Автомобиль не распознан", Toast.LENGTH_SHORT)
                            .show()
                        fragmentManager?.popBackStack()
                    }
                )

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
                    content.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                }
                .subscribe()
        }

        calculate.setOnClickListener {
            if (priceCar != null) {
                val intent = Intent(context, SecondActivity::class.java)
                intent.putExtra("priceCar", priceCar)
                intent.putExtra("nameCar", carName.text)
                startActivity(intent)
            }
        }

        back.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }
}