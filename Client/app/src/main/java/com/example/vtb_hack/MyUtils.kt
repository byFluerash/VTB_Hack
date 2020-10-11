package com.example.vtb_hack

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.setPhoto(uri: Uri?) {
    Glide.with(context)
        .load(uri)
        .centerInside()
        .into(this)
}