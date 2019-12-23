package com.polsl.yerbapp.presentation.views

import android.R.color
import android.graphics.*
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.polsl.yerbapp.R
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
    @BindingAdapter("editable")
    @JvmStatic
    fun editable(view: View, editable: Boolean){

        view.isLongClickable = editable
        view.isClickable = editable
        view.isFocusableInTouchMode = editable
        view.isFocusable = editable
//        if(editable){
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                view.background.colorFilter = BlendModeColorFilter(Color.YELLOW, BlendMode.SRC_ATOP)
//            } else {
//                view.background.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP)
//            }
//        }
        if(editable){
            view.setBackgroundResource(R.drawable.underline)
        }
        else{
            view.setBackgroundColor(Color.TRANSPARENT)
        }

    }
}