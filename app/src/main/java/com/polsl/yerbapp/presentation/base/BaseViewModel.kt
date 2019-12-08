package com.polsl.yerbapp.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent

open class BaseViewModel : ViewModel() {
    protected val _navigationId = LiveEvent<Int>()
    val navigationId: LiveData<Int>
        get() = _navigationId

    protected val _message = LiveEvent<Int>()
    val message: LiveData<Int>
        get() = _message
}