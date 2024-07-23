package com.assignment.openinapp.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.assignment.openinapp.R
import com.bumptech.glide.Glide

object CustomBinder {

    @JvmStatic
    @BindingAdapter("imgUrl")
    fun bindImageUrl(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .error(R.mipmap.ic_launcher)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("date")
    fun bindDate(textView: TextView, timestamp: String?) {
        timestamp?.let {
            textView.text = it.formatTimestamp()
        }
    }
}