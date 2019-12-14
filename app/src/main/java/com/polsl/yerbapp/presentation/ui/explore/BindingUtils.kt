package com.polsl.yerbapp.presentation.ui.explore

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.polsl.yerbapp.R
import com.polsl.yerbapp.domain.models.ProductModel

@BindingAdapter("productName")
fun TextView.setSleepDurationFormatted(item: ProductModel) {
    text = item.name
}

@BindingAdapter("productImage")
fun ImageView.setSleepImage(item: ProductModel) {
    setImageResource(when (item.photoUrl) {
        else -> R.drawable.ic_launcher_foreground
        // 0 -> R.drawable.ic_sleep_0
        // 1 -> R.drawable.ic_sleep_1
        // 2 -> R.drawable.ic_sleep_2
        //
        // 3 -> R.drawable.ic_sleep_3
        //
        // 4 -> R.drawable.ic_sleep_4
        // 5 -> R.drawable.ic_sleep_5
        // else -> R.drawable.ic_sleep_active
    })
}