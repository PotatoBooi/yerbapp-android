package com.polsl.yerbapp.presentation.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.polsl.yerbapp.data.ProductsRepository
import com.polsl.yerbapp.domain.models.ProductModel
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.ui.explore.adapters.ProductsListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExploreViewModel(private val productsRepository: ProductsRepository) : BaseViewModel(), ProductsListener {
    // TODO: Implement the ViewModel


    val  products: LiveData<List<ProductModel>>
        get() = _products

    private val _products = MutableLiveData<List<ProductModel>>()
    init {
        initProducts()
    }

    private fun initProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _products.postValue(productsRepository.getProducts())
    }}

    override fun onItemClick(item: ProductModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


