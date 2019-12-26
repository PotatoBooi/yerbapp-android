package com.polsl.yerbapp.presentation.ui.explore.product_preview

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.polsl.yerbapp.R
import com.polsl.yerbapp.domain.exceptions.NoConnectivityException
import com.polsl.yerbapp.domain.exceptions.UnauthorizedException
import com.polsl.yerbapp.domain.models.reponse.graphql.ManufacturerModel
import com.polsl.yerbapp.domain.models.reponse.graphql.ProductModel
import com.polsl.yerbapp.domain.models.reponse.graphql.TypeModel
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.usecases.ProductsCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductPreviewViewModel(private val productsCase: ProductsCase) : BaseViewModel() {

    val loading = ObservableBoolean(true)

    val product = ObservableField<ProductModel>()
    val productType =  ObservableField<TypeModel>()
    val productManufacturer =  ObservableField<ManufacturerModel>()


    init{
        initProduct()
    }

    private fun initProduct(){
        viewModelScope.launch(Dispatchers.Main) {
            try{
                loading.set(true)
                val productData = productsCase.getProduct()
                product.set(productData)
                productManufacturer.set(productData.manufacturerModel)
                productType.set(productData.typeModel)
                loading.set(false)
            }catch (ex: Exception) {
                loading.set(false)
                handleErrors(ex)
            }
        }
    }

    private fun handleErrors(ex: Exception) {
        when (ex) {
            is UnauthorizedException -> {
                _message.postValue(R.string.UNAUTHORIZED)
                // TODO navigate to login
            }
            is NoConnectivityException -> {
                _message.postValue(R.string.NO_INTERNET)
            }
            else -> _message.postValue(R.string.BAD_RESPONSE)
        }
    }
}