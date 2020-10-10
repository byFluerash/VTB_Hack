package com.example.vtb_hack.data

import com.squareup.moshi.Json

data class RequestStatus (
    @Json(name = "application_status")
    val applicationStatus: String
)