package com.example.vtb_hack.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vtb_hack.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.creditMenu -> {
                }
                R.id.autocreditMenu -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFragment, MainView()).commit()
                }
                R.id.profileMenu -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFragment, ProfileFragment()).commit()
                }
            }

            true
        }

        navigation.selectedItemId = R.id.autocreditMenu
    }
}