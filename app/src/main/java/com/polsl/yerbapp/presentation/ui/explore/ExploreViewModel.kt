package com.polsl.yerbapp.presentation.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polsl.yerbapp.data.ProductsRepository
import com.polsl.yerbapp.domain.models.ProductModel
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.usecases.ProductsCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExploreViewModel(private val productsRepository: ProductsRepository) : BaseViewModel() {
    // TODO: Implement the ViewModel


    private lateinit var products: MutableLiveData<List<ProductModel>>

    fun getProducts(): LiveData<List<ProductModel>> {
        if(!::products.isInitialized){
            products = MutableLiveData()
            // very temporary
            CoroutineScope(Dispatchers.IO).launch {
                products.postValue(productsRepository.getProducts())
            }
        }
        return products
    }

}


