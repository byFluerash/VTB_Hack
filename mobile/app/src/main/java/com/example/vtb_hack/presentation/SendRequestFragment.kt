package com.example.vtb_hack.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.vtb_hack.R
import com.example.vtb_hack.data.Carloan
import com.example.vtb_hack.data.CreditPost
import com.example.vtb_hack.data.RetrofitInstance
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.request_making.*
import java.text.NumberFormat

class SendRequestFragment : Fragment(R.layout.request_making) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val format = NumberFormat.getInstance()

        val loanAmount = arguments!!.getInt("loanAmount")
        val term = arguments!!.getInt("term")
        val contractRate = arguments!!.getDouble("contractRate")
        val carPrice = arguments!!.getDouble("carPrice")
        val fee = arguments!!.getString("fee")!!.toDouble()
        val brand = arguments!!.getString("brand")
        
        fieldEmail.setText(getString(R.string.email_email_example_com))
        fieldName.setText(getString(R.string.narek))
        fieldSecondName.setText(getString(R.string.gevorkyan))
        fieldBirthday.setText(getString(R.string.year_example))
        fieldPhone.setText(getString(R.string.number))


        price.text = getString(R.string.everyMonthPrice, format.format(loanAmount))
        interestRate.text = getString(R.string.interestRate, contractRate.toString())

        sendRequest.setOnClickListener {
            RetrofitInstance.instance.postRequest(
                Carloan(
                    fieldEmail.text.toString(),
                    fee,
                    fieldBirthday.text.toString(),
                    fieldSecondName.text.toString(),
                    fieldName.toString(),
                    fieldPhone.text.toString(),
                    contractRate,
                    loanAmount.toDouble(),
                    term,
                    brand!!,
                    carPrice
                )
            ).subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        RetrofitInstance.instance.postCredit(
                            CreditPost(
                                brand,
                                loanAmount,
                                it.applicationStatus
                            )
                        ).subscribeOn(Schedulers.io())
                            .subscribe({

                            }, {
                                it.printStackTrace()
                            })


                        fragmentManager?.beginTransaction()
                            ?.replace(R.id.secondFragment, MessageRequestFragment())?.commit()
                    },
                    {
                        it.printStackTrace()
                    }
                )
        }

        back.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }
}