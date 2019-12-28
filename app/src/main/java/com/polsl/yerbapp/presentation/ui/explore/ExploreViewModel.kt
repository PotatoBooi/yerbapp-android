package com.polsl.yerbapp.presentation.ui.explore

import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.polsl.yerbapp.R
import com.polsl.yerbapp.domain.exceptions.NoConnectivityException
import com.polsl.yerbapp.domain.exceptions.UnauthorizedException
import com.polsl.yerbapp.domain.models.reponse.graphql.ProductModel
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.base.NavigationProps
import com.polsl.yerbapp.presentation.ui.explore.adapters.ProductsDataFactory
import com.polsl.yerbapp.presentation.ui.explore.adapters.ProductsListener
import com.polsl.yerbapp.presentation.ui.profile.AuthStatus
import com.polsl.yerbapp.presentation.usecases.ProductsCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ExploreViewModel(private val productsCase: ProductsCase) : BaseViewModel(), ProductsListener {

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
        //productsCase.currentProductId = item.id // temporary solution
        val navigationId = R.id.action_exploreFragment_to_previewProductFragment
        val bundle = bundleOf("productId" to item.id)
        _navigationProps.value = NavigationProps(navigationId, bundle)
        //_navigationId.value = R.id.action_exploreFragment_to_previewProductFragment
    }

    fun onAddProductClick() {
        val navigationId = R.id.action_exploreFragment_to_addProductFragment
        _navigationProps.value = NavigationProps(navigationId, null)
        //_navigationId.value = R.id.action_exploreFragment_to_addProductFragment
    }

    private fun initPaging() {

       val productsDataFactory = ProductsDataFactory(viewModelScope, productsCase)
        _loading = Transformations.switchMap(productsDataFactory.liveData){it.loading}

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(12)
            .setPageSize(6)
            .build()
        _pagedProducts = LivePagedListBuilder<Int, ProductModel>(productsDataFactory, config).build()

    }
}




