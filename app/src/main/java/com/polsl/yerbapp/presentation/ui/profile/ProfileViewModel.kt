package com.polsl.yerbapp.presentation.ui.profile

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.polsl.yerbapp.di.useCasesModule
import com.polsl.yerbapp.domain.models.reponse.graphql.ProfileModel
import com.polsl.yerbapp.domain.models.reponse.graphql.UserModel
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.usecases.CurrentUserCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object AuthStatus{
    val LOADING :String = "loading"
    val AUTHORIZED: String = "authorized"
    val UNAUTHORIZED: String = "unauthorized"
}

class ProfileViewModel(private val currentUserCase: CurrentUserCase) : BaseViewModel() {
   // val loginVisible = ObservableBoolean(true)
   // val isAuthorized = ObservableBoolean(false)
    //val isEditable = ObservableBoolean(false)

    private val auth = arrayOf("LOADING", "AUTHORIZED")
    // preferences
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

//    val isAuthorized: LiveData<Boolean>
//        get() = _isAuthorized
//    private val _isAuthorized = MutableLiveData<Boolean>(false)
//
//    val rank: LiveData<Int>
//        get() = _rank
//    private val _rank = MutableLiveData<Int>()

    init {
        checkAuthStatus()
    }

    fun logoutClick() {
        currentUserCase.logoutUser() // TODO live data in repo?
        checkAuthStatus()
    }
    fun saveClick() {
      //TODO
        val editedProfile = ProfileModel(
            priceImportance = price.get()!!.toInt(),
            aromaImportance = aroma.get()!!.toInt(),
            tasteImportance = taste.get()!!.toInt(),
            bitternessImportance = bitterness.get()!!.toInt(),
            energyImportance = energy.get()!!.toInt()
            )
        val editedUser =  UserModel(
            id = "",  // TODO
            username = username.get()!!,
            email = email.get()!!,
            profile = editedProfile)

        viewModelScope.launch (Dispatchers.Main) {
            currentUserCase.editCurrentUser(editedUser)
           // isEditable.set(false)
        }

        // save user info preferences
    }
//    fun editClick(){
//        isEditable.set(true)
//    }
//
//    fun discardClick(){
//        isEditable.set(false)
//    }
//
//    fun checkAuthStatus() = viewModelScope.launch(Dispatchers.Main) {
//        val isAuth = currentUserCase.isUserAuthorized()
//        //loginVisible.set(!isAuth)
//        if (isAuth) isAuthorized.set(true) else isAuthorized.set(false)
//    }

    fun checkAuthStatus() = viewModelScope.launch(Dispatchers.Main) {
        _authStatus.postValue(AuthStatus.LOADING)
        val isAuth = currentUserCase.isUserAuthorized()
        //_isAuthorized.postValue(isAuth)
        if(isAuth) {
            getUser()
        } else {
            _authStatus.postValue(AuthStatus.UNAUTHORIZED)
        }
    }

    private fun getUser(){
        //TODO
        viewModelScope.launch(Dispatchers.Main) {
            val user = currentUserCase.getCurrentUser()
            val profile = user?.profile
            username.set(user?.username)
            email.set(user?.username)
            bitterness.set(profile?.bitternessImportance?.toFloat())
            taste.set(profile?.tasteImportance?.toFloat())
            energy.set(profile?.energyImportance?.toFloat())
            aroma.set(profile?.aromaImportance?.toFloat())
            price.set(profile?.priceImportance?.toFloat())
            _authStatus.postValue(AuthStatus.AUTHORIZED)
        }
    }
}
