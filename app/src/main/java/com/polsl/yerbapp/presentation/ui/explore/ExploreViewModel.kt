package com.polsl.yerbapp.presentation.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.polsl.yerbapp.data.ProductsRepository
import com.polsl.yerbapp.domain.models.ProductModel
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.ui.explore.adapters.ProductsDataSource
import com.polsl.yerbapp.presentation.ui.explore.adapters.ProductsDataSourceFactory

import com.polsl.yerbapp.presentation.ui.explore.adapters.ProductsListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ExploreViewModel(private val productsRepository: ProductsRepository,
                       private val productsDataSourceFactory: ProductsDataSourceFactory
) : BaseViewModel(), ProductsListener {
    // TODO: Implement the ViewModel



    val pagedProducts : LiveData<PagedList<ProductModel>>
        get() = _pagedProducts
    private val _pagedProducts = MutableLiveData<PagedList<ProductModel>>()

//    val liveDataSource: LiveData<PageKeyedDataSource<Int, ProductModel>>
//        get() = _liveDataSource
//    val _liveDataSource = MutableLiveData<PageKeyedDataSource<Int, ProductModel>>()

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

       // _liveDataSource.postValue(productsDataSourceFactory.productsLiveData.value)

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(15)
            .build()
        val sth = initializedPagedListBuilder(pagedListConfig).build()
        _pagedProducts.postValue(sth.value)
       // _pagedProducts.postValue(LivePagedListBuilder(productsDataSourceFactory, pagedListConfig).build().value)

    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, ProductModel> {

        val dataSourceFactory = object : DataSource.Factory<Int, ProductModel>() {
            override fun create(): DataSource<Int, ProductModel> {
                return ProductsDataSource(productsRepository)
            }
        }
        return LivePagedListBuilder<Int, ProductModel>(dataSourceFactory, config)
    }

}


