package com.example.vtb_hack.data

import android.net.Uri

data class Car(
    val id: Long,
    val brand: String,
    val model: String,
    val country: String,
    val price: Int,
    val bodyType: String,
    val doorsCount: Int,
    val colorsCount: Int,
    val photo: Uri
)
