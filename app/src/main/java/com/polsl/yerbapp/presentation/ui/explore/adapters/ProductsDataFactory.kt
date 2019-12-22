package com.polsl.yerbapp.presentation.ui.explore.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.polsl.yerbapp.data.ProductsRepository
import com.polsl.yerbapp.domain.models.ProductModel
import kotlinx.coroutines.CoroutineScope


class ProductsDataFactory(private val scope: CoroutineScope, private val productsRepository: ProductsRepository) : DataSource.Factory<Int, ProductModel>(){

    val liveData: LiveData<ProductsDataSource>
        get() = _mutableLiveData
   private val _mutableLiveData = MutableLiveData<ProductsDataSource>()

    override fun create(): DataSource<Int, ProductModel> {
      val productDataSource =  ProductsDataSource(scope, productsRepository)
        _mutableLiveData.postValue(productDataSource)
        return productDataSource
    }

}