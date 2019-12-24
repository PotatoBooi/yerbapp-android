package com.polsl.yerbapp.presentation.ui.profile

import android.database.Observable
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
   // val loginVisible = ObservableBoolean(true)
    val isAuthorized = ObservableBoolean(false)
    val isEditable = ObservableBoolean(false)

    // preferences
    val bitterness = ObservableField(3)
    val taste = ObservableField(3)
    val energy = ObservableField(3)
    val price = ObservableField(3)
    val aroma = ObservableField(3)

    val username = ObservableField<String>("")
    val email = ObservableField<String>("")



//    val bitterness: LiveData<Int>
//        get() = _bitterness
//    private val _bitterness = MutableLiveData<Int>()
//
//    val taste: LiveData<Int>
//        get() = _taste
//    private val _taste = MutableLiveData<Int>()
//
//    val energy: LiveData<Int>
//        get() = _energy
//    private val _energy = MutableLiveData<Int>()
//
//    val price: LiveData<Int>
//        get() = _price
//    private val _price = MutableLiveData<Int>()
//
//    val aroma: LiveData<Int>
//        get() = _aroma
//    private val _aroma = MutableLiveData<Int>()

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

        // save user info preferences
    }
    fun editClick(){
        isEditable.set(true)
    }

    fun discardClick(){
        isEditable.set(false)
    }

    fun checkAuthStatus() = viewModelScope.launch(Dispatchers.Main) {
        val isAuth = getCurrentUserCase.isUserAuthorized()
        //loginVisible.set(!isAuth)
        if (isAuth) isAuthorized.set(true) else isAuthorized.set(false)
    }

    fun getUser(){
        //TODO
        viewModelScope.launch(Dispatchers.Main) {
            val user = getCurrentUserCase.getCurrentUser()
            val profile = user?.profile
            username.set(user?.username)
            email.set(user?.username)
            bitterness.set(profile?.bitternessImportance)
            taste.set(profile?.tasteImportance)
            energy.set(profile?.energyImportance)
            aroma.set(profile?.aromaImportance)
            price.set(profile?.priceImportance)
        }
    }
}
