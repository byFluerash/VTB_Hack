package com.example.vtb_hack.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class CarDB(
    val brand: String,
    val model: String,
    val country: String,
    val price: Int,
    val bodyType: String,
    val doorsCount: Int,
    val colorsCount: Int,
    val photo: Uri,
    val bigPhoto: Uri
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}
