package com.polsl.yerbapp.presentation.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent

class ProductsSharedViewModel : ViewModel() {
    val refreshList: LiveData<Unit>
        get() = _refreshList
    private val _refreshList = LiveEvent<Unit>()
    fun notifyRefreshList() {
        _refreshList.postValue(Unit)
    }
}