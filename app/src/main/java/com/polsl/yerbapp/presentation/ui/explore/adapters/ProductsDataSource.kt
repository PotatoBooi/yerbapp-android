package com.polsl.yerbapp.presentation.ui.explore.adapters

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
            val items = productsRepository.getProducts()
            callback.onResult(items, 0, 1)   // need to check
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ProductModel>
    ) {
        scope.launch {
            val items = productsRepository.getProducts()
            callback.onResult(items, params.key + 1)   // need to check
        }

    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ProductModel>
    ) {
        scope.launch {
            val items = productsRepository.getProducts()
            callback.onResult(items, params.key + 1)   // need to check
        }
    }

}