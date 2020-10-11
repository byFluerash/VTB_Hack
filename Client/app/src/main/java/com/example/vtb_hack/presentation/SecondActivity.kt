package com.example.vtb_hack.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vtb_hack.R

class SecondActivity : AppCompatActivity(R.layout.second_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = CreditRequestFragment()

        fragment.arguments = Bundle().apply {
            putString("nameCar", intent.extras!!.getString("nameCar"))
            putString("brand", intent.extras!!.getString("brand"))
            putInt("priceCar", intent.extras!!.getInt("priceCar"))
            putInt("term", intent.extras!!.getInt("term"))
            putDouble("carPrice", intent.extras!!.getDouble("carPrice"))
            putString("fee", intent.extras!!.getString("fee"))
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.secondFragment, fragment).commit()
    }
}