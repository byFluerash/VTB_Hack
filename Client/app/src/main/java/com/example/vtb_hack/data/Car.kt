package com.example.vtb_hack.data

import android.net.Uri
import com.squareup.moshi.Json

data class Car(
    @Json(name = "markTitle")
    val brand: String,
    @Json(name = "modelTitle")
    val model: String,
    @Json(name = "country")
    val country: String,
    @Json(name = "price")
    val price: Int,
    @Json(name = "bodyTitle")
    val bodyType: String,
    @Json(name = "doorsCount")
    val doorsCount: Int,
    @Json(name = "colorsCount")
    val colorsCount: Int,
    @Json(name = "widgetPhoto")
    val photo: Uri,
    @Json(name = "mainPhoto")
    val bigPhoto: Uri
)
