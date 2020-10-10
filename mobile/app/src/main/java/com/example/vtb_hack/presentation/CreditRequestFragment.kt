package com.example.vtb_hack.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.vtb_hack.R
import com.example.vtb_hack.data.Calculate
import com.example.vtb_hack.data.RetrofitInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.credit_making.*
import java.text.NumberFormat
import kotlin.math.roundToInt

class CreditRequestFragment : Fragment(R.layout.credit_making) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val format = NumberFormat.getInstance()

        arguments!!.getString("nameCar")

        carName.text = arguments!!.getString("nameCar")
        val carPrice = arguments!!.getInt("priceCar")
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

        var contractRate: Double? = null
        var loanAmount: Int? = null

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
                        contractRate = it.contractRate
                        loanAmount = it.loanAmount
                        Log.d("ContractRate", contractRate.toString())
                        Log.d("LoanAmount", loanAmount.toString())
                    },
                    {
                        it.printStackTrace()
                    }
                )
        }


        getRequest.setOnClickListener {

            if (loanAmount != null && contractRate != null) {

                val fragment = SendRequestFragment()

                fragment.arguments = Bundle().apply {
                    putInt("loanAmount", loanAmount!!)
                    putDouble("contractRate", contractRate!!)
                    putString("fee", fee.text.toString())
                    putString("brand", carName.text.toString().split(" ")[0])
                    putInt("term", seekBar.progress)
                    putDouble("carPrice", carPrice.toDouble())
                }

                fragmentManager?.beginTransaction()?.replace(R.id.secondFragment, fragment)
                    ?.commit()
            }
        }

        back.setOnClickListener {
            activity?.finish()
        }
    }
}