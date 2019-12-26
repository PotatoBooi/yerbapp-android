package com.polsl.yerbapp.presentation.ui.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.polsl.yerbapp.R
import com.polsl.yerbapp.domain.exceptions.NoConnectivityException
import com.polsl.yerbapp.domain.exceptions.UnauthorizedException
import com.polsl.yerbapp.domain.models.reponse.graphql.ProfileModel
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.usecases.CurrentUserCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object AuthStatus{
    const val LOADING :String = "loading"
    const val AUTHORIZED: String = "authorized"
    const val UNAUTHORIZED: String = "unauthorized"
}

class ProfileViewModel(private val currentUserCase: CurrentUserCase) : BaseViewModel() {

    val bitterness = ObservableField(0.0f)
    val taste = ObservableField(0.0f)
    val energy = ObservableField(0.0f)
    val price = ObservableField(0.0f)
    val aroma = ObservableField(0.0f)

    val username = ObservableField<String>("")
    val email = ObservableField<String>("")


    val authStatus: LiveData<String>
        get() = _authStatus
    private val _authStatus = MutableLiveData<String>(AuthStatus.LOADING)

    init {
        checkAuthStatus()
    }

    fun logoutClick() {
        currentUserCase.logoutUser()
        checkAuthStatus()
    }
    fun saveClick() {
      //TODO mapper
        val editedProfile = ProfileModel(
            priceImportance = price.get()!!.toInt(),
            aromaImportance = aroma.get()!!.toInt(),
            tasteImportance = taste.get()!!.toInt(),
            bitternessImportance = bitterness.get()!!.toInt(),
            energyImportance = energy.get()!!.toInt()
            )
            viewModelScope.launch (Dispatchers.Main) {
                try{
                    currentUserCase.editCurrentUser(editedProfile)
                }catch (ex: Exception) {
                    handleErrors(ex)
                }
            }
    }


    fun checkAuthStatus() {
        viewModelScope.launch(Dispatchers.Main) {
            try{
                _authStatus.postValue(AuthStatus.LOADING)
                val isAuth = currentUserCase.isUserAuthorized()
                if (isAuth) {
                    getUser()
                } else {
                    _authStatus.postValue(AuthStatus.UNAUTHORIZED)
                }
            }catch (ex: Exception) {
                _authStatus.postValue(AuthStatus.UNAUTHORIZED)
                handleErrors(ex)
            }

        }
    }

    private fun getUser(){
        //TODO mapper
        viewModelScope.launch(Dispatchers.Main) {
            try{
                val user = currentUserCase.getCurrentUser()
                val profile = user?.profile
                username.set(user?.username)
                email.set(user?.email)
                bitterness.set(profile?.bitternessImportance?.toFloat())
                taste.set(profile?.tasteImportance?.toFloat())
                energy.set(profile?.energyImportance?.toFloat())
                aroma.set(profile?.aromaImportance?.toFloat())
                price.set(profile?.priceImportance?.toFloat())
                _authStatus.postValue(AuthStatus.AUTHORIZED)
            }catch (ex: Exception) {
                handleErrors(ex)
            }

        }
    }

    private fun handleErrors(ex: Exception) {
        when (ex) {
            is UnauthorizedException -> {
                _message.postValue(R.string.UNAUTHORIZED)
            }
            is NoConnectivityException -> {
                _message.postValue(R.string.NO_INTERNET)
            }
            else -> _message.postValue(R.string.BAD_RESPONSE)
        }
    }
}
