package com.example.vtb_hack.data

import android.net.Uri
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class UriJsonAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): Uri? {
        reader.apply {
            return when (peek()) {
                JsonReader.Token.NULL -> {
                    nextNull()
                }
                JsonReader.Token.STRING -> {
                    val string = nextString()
                    if (string.isNotEmpty()) Uri.parse(string)
                    else null
                }
                else -> {
                    null
                }
            }
        }
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: Uri?) {
        if (value != null) {
            writer.value(value.toString())
        }
    }
}