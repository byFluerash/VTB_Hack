package com.example.vtb_hack.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.vtb_hack.R
import kotlinx.android.synthetic.main.profile.*

class ProfileFragment: Fragment(R.layout.profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(context!!)
            .load(R.drawable.ic_nerd_man_with_curly_hair_and_circular_eyeglasses_avatar)
            .centerInside()
            .into(photo)
    }
}