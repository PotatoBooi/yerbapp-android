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

//
//    private fun getTempProducts(): LiveData<List<Product>> {
//        var temp = MutableLiveData<List<Product>>()
//        temp.postValue(listOf(Product("item 1", "image 1"), Product("item 2", "image 2"),
//            Product("item 3", "image 3"), Product("item 4", "image 4")))
//
//       productsCase.testProducts()
//
//        return temp
//    }
//
//    val products = getTempProducts()


   // val products = getProduct()

    private lateinit var products: MutableLiveData<List<ProductModel>>

    fun getProducts(): LiveData<List<ProductModel>> {
        if(!::products.isInitialized){
            products = MutableLiveData()
            // very temporary
            CoroutineScope(Dispatchers.IO).launch {
                products.postValue(listOf(productsRepository.getProducts()))
            }
        }
        return products
    }
//    val products:LiveData<ProductModel>
//        get() = _products

}

//// temporary for testing
//data class Product(
//    var name: String,
//    var image: String
//)

