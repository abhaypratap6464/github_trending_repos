package com.android.example.demomvvm.core.util

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.android.example.demomvvm.R
import com.bumptech.glide.Glide


@BindingAdapter("avatarImage")
fun ImageView.fetchImage(src: String?) {
    if (src != null) {
        val uri = src.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(uri)
            .centerCrop()
            .error(android.R.drawable.stat_notify_error)
            .into(this)
    } else {
        setImageResource(R.drawable.ic_profile)
    }
}






