package com.polsl.yerbapp.presentation.ui.profile

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.usecases.GetCurrentUserCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val getCurrentUserCase: GetCurrentUserCase) : BaseViewModel() {
    val loginVisible = ObservableBoolean(true)
    val isAuthorized = ObservableField<String>("")
    val isEditable = ObservableBoolean(false)

    val username: LiveData<String>
        get() = _username
    private val _username = MutableLiveData<String>()

    val email: LiveData<String>
        get() = _email
    private val _email = MutableLiveData<String>()

    val rank: LiveData<Int>
        get() = _rank
    private val _rank = MutableLiveData<Int>()

    init {
        checkAuthStatus()
    }

    fun logoutClick() {
        getCurrentUserCase.logoutUser()
        checkAuthStatus()
    }
    fun saveClick() {
      //TODO
        isEditable.set(false)
    }
    fun editClick(){
        isEditable.set(true)
    }

    fun discardClick(){
        isEditable.set(false)
    }

    fun checkAuthStatus() = viewModelScope.launch(Dispatchers.Main) {
        val isAuth = getCurrentUserCase.isUserAuthorized()
        loginVisible.set(!isAuth)
        if (isAuth) isAuthorized.set("Authorized!") else isAuthorized.set("Not Authorized!")
    }
}
