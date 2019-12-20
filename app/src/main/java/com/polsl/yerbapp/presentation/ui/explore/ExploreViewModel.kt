package com.polsl.yerbapp.presentation.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.polsl.yerbapp.data.ProductsRepository
import com.polsl.yerbapp.domain.models.ProductModel
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.ui.explore.adapters.ProductsDataSource

import com.polsl.yerbapp.presentation.ui.explore.adapters.ProductsListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ExploreViewModel(private val productsRepository: ProductsRepository) : BaseViewModel(), ProductsListener {
    // TODO: Implement the ViewModel



    lateinit var pagedProducts : LiveData<PagedList<ProductModel>>
       // get() = _pagedProducts
   // private val _pagedProducts = MutableLiveData<PagedList<ProductModel>>()


    val  products: LiveData<List<ProductModel>>
        get() = _products

    private val _products = MutableLiveData<List<ProductModel>>()

    init {
        //initProducts()
        initPaging()

    }

    private fun initProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _products.postValue(productsRepository.getProducts())
    }}

    override fun onItemClick(item: ProductModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initPaging() {

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(15)
            .build()
        pagedProducts = initializedPagedListBuilder(pagedListConfig).build()
    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, ProductModel> {

        val dataSourceFactory = object : DataSource.Factory<Int, ProductModel>() {
            override fun create(): DataSource<Int, ProductModel> {
                return ProductsDataSource(viewModelScope, productsRepository)
            }
        }
        return LivePagedListBuilder<Int, ProductModel>(dataSourceFactory, config)
    }

}


