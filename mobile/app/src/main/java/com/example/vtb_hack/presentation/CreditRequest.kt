package com.example.vtb_hack.presentation

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.vtb_hack.R
import com.example.vtb_hack.data.Calculate
import com.example.vtb_hack.data.RetrofitInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.credit_making.*
import java.text.NumberFormat
import kotlin.math.roundToInt

class CreditRequest : AppCompatActivity(R.layout.credit_making) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val format = NumberFormat.getInstance()

        carName.text = intent.extras!!.getString("nameCar")
        val carPrice = intent.extras!!.getInt("priceCar")
        everyMonthPay.text = getString(R.string.everyMonthPrice, "0")
        price.text = getString(R.string.carPrice, format.format(carPrice))
        val casco = (carPrice * 0.1).roundToInt()
        countPrice.text = getString(R.string.countCASCO, format.format(casco))
        totalPrice.text = getString(R.string.total, format.format(casco + carPrice))
        stepPrice.text = getString(R.string.maxPriceFee, format.format(casco + carPrice))

        labelPeriodCredit.text = getString(R.string.periodCredit, seekBar.progress)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                labelPeriodCredit.text = getString(R.string.periodCredit, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        calculate.setOnClickListener {
            RetrofitInstance.instance.calculate(
                Calculate(
                    carPrice,
                    fee.text.toString().toInt(),
                    casco,
                    seekBar.progress
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        everyMonthPay.text =
                            getString(R.string.everyMonthPrice, format.format(it.monthPayment))
                    },
                    {
                        it.printStackTrace()
                    }
                )
        }
    }
}