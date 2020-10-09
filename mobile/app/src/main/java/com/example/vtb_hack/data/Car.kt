package com.example.vtb_hack.data

import android.net.Uri

data class Car(
    val id: Long,
    val brand: String,
    val model: String,
    val country: String,
    val price: Int,
    val colorsCount: Int,
    val photo: Uri
)
