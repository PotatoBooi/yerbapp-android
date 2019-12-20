package com.polsl.yerbapp.presentation.ui.explore.adapters

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.polsl.yerbapp.data.ProductsRepository
import com.polsl.yerbapp.domain.models.ProductModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductsDataSource (private val productsRepository: ProductsRepository):
    PageKeyedDataSource<Int, ProductModel>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ProductModel>
    ) {
        val items = initProducts()
        callback.onResult(items, 0, 1) // need to check
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ProductModel>
    ) {
        val items = initProducts()
        callback.onResult(items, params.key + 1) // need to check
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ProductModel>
    ) {
        val items = initProducts()
        callback.onResult(items, params.key + 1) // need to check
    }

    private fun initProducts() : ArrayList<ProductModel> {

        val productsList: ArrayList<ProductModel> = ArrayList()
        CoroutineScope(Dispatchers.Main).launch{
            productsList.addAll(productsRepository.getProducts())
        }
        return productsList
    }
}