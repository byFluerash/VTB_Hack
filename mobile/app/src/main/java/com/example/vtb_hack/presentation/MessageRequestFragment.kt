package com.example.vtb_hack.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.vtb_hack.R
import kotlinx.android.synthetic.main.request_making.back
import kotlinx.android.synthetic.main.request_message.*

class MessageRequestFragment: Fragment(R.layout.request_message) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        back.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        accept.setOnClickListener {
            activity?.finish()
        }
    }
}