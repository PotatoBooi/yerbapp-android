package com.polsl.yerbapp.presentation.views

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.polsl.yerbapp.presentation.ui.helpers.GlideApp

object BindingAdapters {
    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImage(imageView: ImageView, url: String) {
        GlideApp.with(imageView).load(url).apply(RequestOptions().fitCenter()).into(imageView)
    }
}