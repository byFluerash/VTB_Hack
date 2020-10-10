package com.example.vtb_hack.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.vtb_hack.R
import com.example.vtb_hack.data.CarDB
import com.example.vtb_hack.data.DataBase
import com.example.vtb_hack.data.RetrofitInstance
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        DataBase.initDataBase(this)

        Completable.complete()
            .subscribeOn(Schedulers.io())
            .doOnComplete {
                DataBase.getDataBase().carDAO().deleteAll()
            }
            .subscribe()

        RetrofitInstance.instance.getCars()
            .doOnError {
                Log.d("Retrofit Error", it.localizedMessage)
            }
            .flatMapIterable { list -> list }
            .doOnNext {
                val carDB = CarDB(
                    it.brand,
                    it.model,
                    it.country,
                    it.price,
                    it.bodyType,
                    it.doorsCount,
                    it.colorsCount,
                    it.photo,
                    it.bigPhoto
                )
                DataBase.getDataBase().carDAO().insert(carDB)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.d("Retrofit Error, MainThread", it.localizedMessage)
            }
            .doOnComplete {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .subscribe()
    }
}