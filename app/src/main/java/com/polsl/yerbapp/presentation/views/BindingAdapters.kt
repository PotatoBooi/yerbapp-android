package com.polsl.yerbapp.presentation.views


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.*
import com.bumptech.glide.request.RequestOptions
import com.polsl.yerbapp.R
import com.polsl.yerbapp.presentation.ui.helpers.GlideApp
import com.willy.ratingbar.BaseRatingBar
import java.nio.file.WatchEvent


object BindingAdapters {

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("productImage")
    @JvmStatic
    fun setProductImage(imageView: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            GlideApp.with(imageView).load(url).apply(RequestOptions().fitCenter()).into(imageView)
        } else {
            GlideApp.with(imageView).load(R.drawable.product_default)
                .apply(RequestOptions().fitCenter()).into(imageView)
        }
    }

    @BindingAdapter("addProductImage")
    @JvmStatic
    fun setAddProductImage(imageView: ImageView, path: String?) {
        if (path.isNullOrEmpty()) {
            GlideApp.with(imageView).load(R.drawable.ic_add_photo)
                .apply(RequestOptions().fitCenter()).into(imageView)
        } else {
            GlideApp.with(imageView).load(path).apply(RequestOptions().fitCenter()).into(imageView)
        }
    }

    @BindingAdapter("imageRes")
    @JvmStatic
    fun setImageFromRes(imageView: ImageView, res: Int?) {
        GlideApp.with(imageView).load(res).apply(RequestOptions().fitCenter()).into(imageView)
    }

    @BindingAdapter("textFloat")
    @JvmStatic
    fun setText(textView: TextView, value: Float) {
        textView.text = String.format("%.1f", value)
    }

    @BindingAdapter("isIndicator")
    @JvmStatic
    fun setIsIndicator(ratingBar: BaseRatingBar, value: Boolean) {
        ratingBar.setIsIndicator(value)
    }

        @InverseBindingMethods(
            InverseBindingMethod(
                type = com.willy.ratingbar.BaseRatingBar::class,
                attribute = "rate"
            )
        )

    class RatingBarBinder {
        companion object {
            @BindingAdapter("app:rateAttrChanged")
            @JvmStatic
            fun setListener(ratingBar: BaseRatingBar, attrChange: InverseBindingListener){
                ratingBar.setOnRatingChangeListener { _, _, _ ->
                    attrChange.onChange()
                }
            }

            @InverseBindingAdapter(attribute = "rate")//, event = "ratingAttrChanged")
            @JvmStatic
            fun getRate(ratingBar: BaseRatingBar) = ratingBar.rating

            @BindingAdapter("rate")
            @JvmStatic
            fun setRate(ratingBar: BaseRatingBar, rate: Float) {
                if (ratingBar.rating != rate) {
                    ratingBar.rating = rate
                }
            }
        }
    }

}

