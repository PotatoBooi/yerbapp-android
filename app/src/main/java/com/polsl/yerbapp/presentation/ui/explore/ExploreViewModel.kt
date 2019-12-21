package com.polsl.yerbapp.presentation.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.polsl.yerbapp.data.ProductsRepository
import com.polsl.yerbapp.domain.models.ProductModel
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.ui.explore.adapters.ProductsDataSource
import com.polsl.yerbapp.presentation.ui.explore.adapters.ProductsListener


class ExploreViewModel(private val productsRepository: ProductsRepository) : BaseViewModel(), ProductsListener {

    init {
        initPaging()
    }

    private val PAGE_SIZE: Int = 8

    val pagedProducts : LiveData<PagedList<ProductModel>>
        get() = _pagedProducts
    private lateinit var _pagedProducts: LiveData<PagedList<ProductModel>>


    override fun onItemClick(item: ProductModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initPaging() {

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .build()
        _pagedProducts = initializedPagedListBuilder(pagedListConfig).build()
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


