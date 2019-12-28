package com.polsl.yerbapp.presentation.ui.explore.add_product

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.polsl.yerbapp.domain.models.reponse.graphql.ManufacturerModel
import com.polsl.yerbapp.domain.models.reponse.graphql.TypeModel
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.usecases.ProductsCase


class AddProductViewModel(private val productsCase: ProductsCase) : BaseViewModel() {
    val loading = ObservableBoolean(true)

    val name = ObservableField<String>("")
    val details = ObservableField<String>("")

    val types = ObservableArrayList<TypeModel>()
    val selectedTypeIndex = ObservableInt(0)

    val manufacturers = ObservableArrayList<ManufacturerModel>()
    val selectedManufacturerIndex = ObservableInt(0)

    init{
        // TODO load manufacurers and types

    }

    fun onSaveClick() {

        // TODO validation
        // TODO Post photo or default photo
        // TODO add product with url photo adress
    }
}
