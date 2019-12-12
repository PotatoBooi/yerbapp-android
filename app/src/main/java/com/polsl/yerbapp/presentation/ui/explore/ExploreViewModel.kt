package com.polsl.yerbapp.presentation.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polsl.yerbapp.presentation.base.BaseViewModel

class ExploreViewModel : BaseViewModel() {
    // TODO: Implement the ViewModel

    private fun getTempProducts(): LiveData<List<String>> {
        var temp = MutableLiveData<List<String>>()
        temp.postValue(listOf("one", "two", "three", "four"))
      return temp
    }
    val products = getTempProducts()



}
