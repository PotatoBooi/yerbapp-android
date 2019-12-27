package com.polsl.yerbapp.presentation.ui.explore.add_product

import androidx.databinding.ObservableBoolean
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.usecases.ProductsCase


class AddProductViewModel(private val productsCase: ProductsCase) : BaseViewModel() {
    val loading = ObservableBoolean(true)
}
