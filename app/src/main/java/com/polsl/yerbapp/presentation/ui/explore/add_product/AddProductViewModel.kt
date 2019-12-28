package com.polsl.yerbapp.presentation.ui.explore.add_product

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polsl.yerbapp.domain.models.reponse.graphql.TypeModel
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.usecases.ProductsCase


class AddProductViewModel(private val productsCase: ProductsCase) : BaseViewModel() {
    val loading = ObservableBoolean(true)

    val types : LiveData<List<String>>
        get() = _types

    private val _types = MutableLiveData<List<String>>()
    init{
        val list = listOf("1", "2", "3")
        _types.postValue(list)
    }
}
