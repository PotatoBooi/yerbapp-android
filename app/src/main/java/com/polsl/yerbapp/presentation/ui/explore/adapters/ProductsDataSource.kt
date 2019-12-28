package com.polsl.yerbapp.presentation.ui.explore.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.polsl.yerbapp.domain.models.reponse.graphql.ProductModel
import com.polsl.yerbapp.presentation.usecases.ProductsCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class ProductsDataSource (private val scope: CoroutineScope, private val productsCase: ProductsCase):
    PageKeyedDataSource<Int, ProductModel>() {

    val loading: LiveData<Boolean>
        get() = _loading
    private val _loading =  MutableLiveData<Boolean>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ProductModel>
    ) {
        _loading.postValue(true)
        scope.launch {
            try{
                val items = productsCase.getProducts(params.requestedLoadSize, 0 )
                _loading.postValue(false)
                callback.onResult(items, null, params.requestedLoadSize)
            } catch(ex: Exception){
                // TODO notification
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ProductModel>
    ) {
        _loading.postValue(true)
        scope.launch {
            try{
                val items = productsCase.getProducts(params.requestedLoadSize, params.key)
                _loading.postValue(false)
                callback.onResult(items, params.key + params.requestedLoadSize)
            } catch(ex: Exception){
                // TODO notification
            }
        }

    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ProductModel>
    ) {
    }

}