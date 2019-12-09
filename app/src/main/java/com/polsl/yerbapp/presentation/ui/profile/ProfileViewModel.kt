package com.polsl.yerbapp.presentation.ui.profile

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.usecases.GetCurrentUserCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val getCurrentUserCase: GetCurrentUserCase) : BaseViewModel() {
    val loginVisible = ObservableBoolean(true)
    val isAuthorized = ObservableField<String>("")

    init {
        checkAuthStatus()
    }

    fun logoutClick() {
        getCurrentUserCase.logoutUser()
        checkAuthStatus()
    }

    fun checkAuthStatus() = viewModelScope.launch(Dispatchers.Main) {
        val isAuth = getCurrentUserCase.isUserAuthorized()
        loginVisible.set(!isAuth)
        if (isAuth) isAuthorized.set("Authorized!") else isAuthorized.set("Not Authorized!")
    }
}
