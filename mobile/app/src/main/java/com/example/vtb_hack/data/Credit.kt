package com.example.vtb_hack.data

import com.squareup.moshi.Json

data class Credit (
    @Json(name = "car")
    val carName: String,
    @Json(name = "state")
    val state: String,
    @Json(name = "requested_amount")
    val sum: Int
)