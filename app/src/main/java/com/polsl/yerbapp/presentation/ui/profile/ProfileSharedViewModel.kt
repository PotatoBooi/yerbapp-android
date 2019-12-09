package com.polsl.yerbapp.presentation.ui.profile

import androidx.lifecycle.LiveData
import com.hadilq.liveevent.LiveEvent
import com.polsl.yerbapp.presentation.base.BaseViewModel

class ProfileSharedViewModel : BaseViewModel() {
    val registerSuccess: LiveData<String>
        get() = _registerSuccess
    private val _registerSuccess = LiveEvent<String>()
    fun handleRegisterSucces(username: String) {
        _registerSuccess.postValue(username)
    }
}