package com.polsl.yerbapp.presentation.ui.profile.register

import android.text.TextUtils
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.polsl.yerbapp.R
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.usecases.RegisterUserCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerUserCase: RegisterUserCase) : BaseViewModel() {
    val loading = ObservableBoolean(false)
    val loginInput = ObservableField<String>("")
    val emailInput = ObservableField<String>("")
    val passwordInput = ObservableField<String>("")
    val notifyRegisterSuccess: LiveData<String>
        get() = _notifyRegisterSuccess
    private val _notifyRegisterSuccess = LiveEvent<String>()
    fun registerClick() {
        when {
            loginInput.get()?.isEmpty() ?: true -> {
                _message.value = R.string.USERNAME_EMPTY
                return
            }
            (loginInput.get()?.length ?: 0) < 7 -> {
                    _message.value = R.string.USERNAME_TOO_SHORT
                return
            }
        }
        when {
            passwordInput.get()?.isEmpty() ?: true -> {
                _message.value = R.string.PASSWORD_EMPTY
                return
            }
            (passwordInput.get()?.length ?: 0) < 8 -> {
                _message.value = R.string.PASSWORD_TOO_SHORT
            }
        }
        when {
            emailInput.get()?.isEmpty() ?: true -> {
                _message.value = R.string.EMAIL_EMPTY
                return
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput.get()).matches() -> {
                _message.value = R.string.EMAIL_BAD_PATTER
                return
            }
        }
        viewModelScope.launch(Dispatchers.Main) {
            try {
                loading.set(true)
                val result = registerUserCase.registerUser(
                    loginInput.get() ?: "",
                    emailInput.get() ?: "",
                    passwordInput.get() ?: ""
                )
                _notifyRegisterSuccess.postValue(result.username)
            } catch (ex: Exception) {
                handleErrors(ex)
            } finally {
                loading.set(false)
            }
        }
    }

    private fun handleErrors(exception: Exception) {
        _message.postValue(R.string.ERROR)
    }

    fun loginClick(){
        _navigationId.value = R.id.action_registerFragment_to_profileFragment
    }
}
