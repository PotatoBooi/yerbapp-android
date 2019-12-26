package com.polsl.yerbapp.presentation.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.polsl.yerbapp.data.ProductsRepository
import com.polsl.yerbapp.domain.models.reponse.graphql.ProductModel
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.ui.explore.adapters.ProductsDataFactory
import com.polsl.yerbapp.presentation.ui.explore.adapters.ProductsListener


class ExploreViewModel(private val productsRepository: ProductsRepository) : BaseViewModel(), ProductsListener {

    init {
        initPaging()
    }


    val pagedProducts : LiveData<PagedList<ProductModel>>
        get() = _pagedProducts
    private lateinit var _pagedProducts: LiveData<PagedList<ProductModel>>

    val loading: LiveData<Boolean>
        get() = _loading

    private lateinit var  _loading: LiveData<Boolean>

    override fun onItemClick(item: ProductModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initPaging() {

       val productsDataFactory = ProductsDataFactory(viewModelScope, productsRepository)
        _loading = Transformations.switchMap(productsDataFactory.liveData){it.loading}

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(12)
            .setPageSize(6)
            .build()
        _pagedProducts = LivePagedListBuilder<Int, ProductModel>(productsDataFactory, config).build()

    }


}


