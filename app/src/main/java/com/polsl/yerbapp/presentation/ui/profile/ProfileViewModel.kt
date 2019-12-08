package com.polsl.yerbapp.presentation.ui.profile

import com.polsl.yerbapp.R
import com.polsl.yerbapp.presentation.base.BaseViewModel

class ProfileViewModel : BaseViewModel() {
    fun registerClick() {
        _navigationId.value = R.id.action_profileFragment_to_registerFragment
    }
}
