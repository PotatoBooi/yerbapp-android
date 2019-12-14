package com.polsl.yerbapp.presentation.ui.explore.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.polsl.yerbapp.domain.models.ProductModel
import com.polsl.yerbapp.presentation.ui.explore.GlideApp


@BindingAdapter("productName")
fun TextView.setText(item: ProductModel) {
    text = item.name
}

@BindingAdapter("productImage")
fun ImageView.setImage(url: String) {
    //val url = item.photoUrl
    //var requestOptions = RequestOptions()
    //requestOptions = requestOptions.placeholder(R.drawable.boy_32)
    GlideApp.with(this).load(url).apply(RequestOptions().override(400)).into(this)

    //setImage(url)
//    GlideApp.with(this.context)
//        .load(url)
//        .into(this)
}



