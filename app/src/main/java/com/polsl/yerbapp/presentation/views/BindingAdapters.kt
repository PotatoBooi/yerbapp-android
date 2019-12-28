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
    fun setImage(imageView: ImageView, url: String?) {
        GlideApp.with(imageView).load(url).apply(RequestOptions().fitCenter()).into(imageView)
    }
//    @BindingAdapter("editable")
//    @JvmStatic
//    fun editable(view: View, editable: Boolean){
//
//        view.isLongClickable = editable
//        view.isClickable = editable
//        view.isFocusableInTouchMode = editable
//        view.isFocusable = editable
//
//        if(editable){
//            view.setBackgroundResource(R.drawable.underline)
//        }
//        else{
//            view.setBackgroundColor(Color.TRANSPARENT)
//        }
//    }

}