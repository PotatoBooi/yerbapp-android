package com.polsl.yerbapp.presentation.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polsl.yerbapp.presentation.base.BaseViewModel

class ExploreViewModel : BaseViewModel() {
    // TODO: Implement the ViewModel

    private fun getTempProducts(): LiveData<List<Product>> {
        var temp = MutableLiveData<List<Product>>()
        temp.postValue(listOf(Product("item 1", "image 1"), Product("item 2", "image 2"),
            Product("item 3", "image 3"), Product("item 4", "image 4")))
      return temp
    }

    val products = getTempProducts()

}

// temporary for testing
data class Product(
    var name: String,
    var image: String
)