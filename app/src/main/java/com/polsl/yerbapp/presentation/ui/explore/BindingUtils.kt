package com.polsl.yerbapp.presentation.ui.explore

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.polsl.yerbapp.R

@BindingAdapter("productName")
fun TextView.setSleepDurationFormatted(item: Product) {
    text = item.name
}

@BindingAdapter("productImage")
fun ImageView.setSleepImage(item: Product) {
    setImageResource(when (item.image) {
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