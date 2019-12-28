package com.polsl.yerbapp.presentation.ui.profile.login

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.polsl.yerbapp.R
import com.polsl.yerbapp.domain.exceptions.InvalidCredentialsException
import com.polsl.yerbapp.domain.exceptions.NoConnectivityException
import com.polsl.yerbapp.domain.exceptions.UserNotFoundException
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.base.NavigationProps
import com.polsl.yerbapp.presentation.usecases.LoginUserCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUserCase: LoginUserCase) : BaseViewModel() {
    val loginInput = ObservableField<String>("")
    val passwordInput = ObservableField<String>("")
    val loading = ObservableBoolean(false)
    val registerVisible = ObservableBoolean(true)
    val notifyUserLoggedIn: LiveData<Unit>
        get() = _notifyUserLoggedIn
    private val _notifyUserLoggedIn = LiveEvent<Unit>()
    fun loginClick() {
        when {
            loginInput.get()?.isEmpty() ?: true -> {
                _message.value = R.string.USERNAME_EMPTY
                return
            }
            (loginInput.get()?.length ?: 0) < 8 -> {

            }
        }
        when {
            passwordInput.get()?.isEmpty() ?: true -> {
                _message.value = R.string.PASSWORD_EMPTY
                return
            }
            (passwordInput.get()?.length ?: 0) < 8 -> {

            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            loading.set(true)
            try {
                loginUserCase.loginUser(loginInput.get() ?: "", passwordInput.get() ?: "")
                _notifyUserLoggedIn.postValue(Unit)
                loginInput.set("")
                passwordInput.set("")
            } catch (ex: Exception) {
                handleErrors(ex)
            } finally {
                loading.set(false)
            }
        }
    }

    fun registerClick() {
       val navigationId = R.id.action_profileFragment_to_registerFragment
        _navigationProps.value = NavigationProps(navigationId, null)
    }

    private fun handleErrors(ex: Exception) {
        when (ex) {
            is UserNotFoundException -> {
                _message.postValue(R.string.USER_NOT_FOUND)
            }
            is InvalidCredentialsException -> {
                _message.postValue(R.string.INVALID_CREDENTIALS)
            }
            is NoConnectivityException -> {
                _message.postValue(R.string.NO_INTERNET)
            }
            else -> _message.postValue(R.string.BAD_RESPONSE)
        }
    }

    fun onRegisterSuccess(username: String) {
        loginInput.set(username)
        registerVisible.set(false)
    }
}
