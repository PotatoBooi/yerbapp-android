package com.polsl.yerbapp.presentation.ui.explore.adapters

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.polsl.yerbapp.data.ProductsRepository
import com.polsl.yerbapp.domain.models.ProductModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class ProductsDataSource (private val scope: CoroutineScope, private val productsRepository: ProductsRepository):
    PageKeyedDataSource<Int, ProductModel>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ProductModel>
    ) {
        scope.launch {
            val items = productsRepository.getProducts(params.requestedLoadSize, 0 )
            callback.onResult(items, null, params.requestedLoadSize)
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ProductModel>
    ) {
        scope.launch {
            val items = productsRepository.getProducts(params.requestedLoadSize, params.key)
            callback.onResult(items, params.key + params.requestedLoadSize)
        }

    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ProductModel>
    ) {
    }

}