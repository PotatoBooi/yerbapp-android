package com.polsl.yerbapp.presentation.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent

data class NavigationProps(
    val navigationId: Int,
    val bundle: Bundle?
)

open class BaseViewModel : ViewModel() {
    protected val _navigationProps = LiveEvent<NavigationProps>()
    val navigationProps: LiveData<NavigationProps>
        get() = _navigationProps

    protected val _message = LiveEvent<Int>()
    val message: LiveData<Int>
        get() = _message
}