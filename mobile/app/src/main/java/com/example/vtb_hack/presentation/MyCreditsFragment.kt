package com.example.vtb_hack.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vtb_hack.R
import com.example.vtb_hack.data.RetrofitInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.my_credits.*

class MyCreditsFragment: Fragment(R.layout.my_credits) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val res = RetrofitInstance.instance.getCredits()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    creditRecycler.adapter = CreditAdapter(it)
                },
                {
                    Toast.makeText(context!!, "Кредитов нет", Toast.LENGTH_SHORT).show()
                }
            )
    }
}