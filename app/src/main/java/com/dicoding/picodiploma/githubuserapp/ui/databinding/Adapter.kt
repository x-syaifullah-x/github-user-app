package com.dicoding.picodiploma.githubuserapp.ui.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object Adapter {

    @JvmStatic
    @BindingAdapter("set_image")
    fun setImageItem(v: ImageView, url: String?) {
        url?.apply {
            Glide.with(v)
                .load(url)
                .centerCrop()
                .into(v)
        }
    }
}