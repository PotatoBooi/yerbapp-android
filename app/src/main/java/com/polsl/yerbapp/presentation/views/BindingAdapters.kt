package com.polsl.yerbapp.presentation.views


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.request.RequestOptions
import com.polsl.yerbapp.R
import com.polsl.yerbapp.presentation.ui.helpers.GlideApp
import com.willy.ratingbar.BaseRatingBar


object BindingAdapters {

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("productImage")
    @JvmStatic
    fun setProductImage(imageView: ImageView, url: String?) {
        if(!url.isNullOrEmpty()){
            GlideApp.with(imageView).load(url).apply(RequestOptions().fitCenter()).into(imageView)
        } else{
            GlideApp.with(imageView).load(R.drawable.product_default).apply(RequestOptions().fitCenter()).into(imageView)
        }
    }

    @BindingAdapter("addProductImage")
    @JvmStatic
    fun setAddProductImage(imageView: ImageView, path: String?){
        if(path.isNullOrEmpty()) {
            GlideApp.with(imageView).load(R.drawable.ic_add_photo).apply(RequestOptions().fitCenter()).into(imageView)
        }else{
            GlideApp.with(imageView).load(path).apply(RequestOptions().fitCenter()).into(imageView)
        }
    }

    @BindingAdapter("imageRes")
    @JvmStatic
    fun setImageFromRes(imageView: ImageView, res: Int?) {
        GlideApp.with(imageView).load(res).apply(RequestOptions().fitCenter()).into(imageView)
    }

    @BindingAdapter("rate")
    @JvmStatic
    fun setRate(bar: BaseRatingBar, rate: Float){
        if(bar.rating != rate){
            bar.rating = rate
        }
    }

    @BindingAdapter("textFloat")
    @JvmStatic
    fun setText(textView: TextView, value: Float){
        textView.text = value.toString()
    }

//    @InverseBindingAdapter(attribute = "rate")
//    @JvmStatic
//    fun getRate(ratingBar: BaseRatingBar): Float{
//        return ratingBar.rating
//    }
//
//    @BindingAdapter("app:rateAttrChanged")
//    @JvmStatic fun setListeners(
//        rateingBar: BaseRatingBar,
//        attrChange: InverseBindingListener
//    ) {
//
//        // Set a listener for click, focus, touch, etc.
//    }

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