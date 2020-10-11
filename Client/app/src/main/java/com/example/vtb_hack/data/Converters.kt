package com.example.vtb_hack.data

import android.net.Uri
import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(value: String?): Uri? {
        return Uri.parse(value)
    }

    @TypeConverter
    fun UriToString(value: Uri?): String? {
        return value.toString()
    }
}