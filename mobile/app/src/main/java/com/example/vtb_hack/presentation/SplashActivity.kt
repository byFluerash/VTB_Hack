package com.example.vtb_hack.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.vtb_hack.R
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        Completable.complete()
            .subscribeOn(Schedulers.io())
            .timeout(500, TimeUnit.MILLISECONDS)
            .doOnComplete {
                Log.d("RxJava", "subscribe")
                startActivity(Intent(this, MainActivity::class.java))
            }
            .subscribe()
    }
}