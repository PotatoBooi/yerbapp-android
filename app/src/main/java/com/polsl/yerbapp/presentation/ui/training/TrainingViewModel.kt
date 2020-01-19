package com.polsl.yerbapp.presentation.ui.training


import com.polsl.yerbapp.R
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.base.NavigationProps

class TrainingViewModel : BaseViewModel() {


    fun onStartTutorialClick() {
        val navigationId = R.id.action_trainingFragment_to_tutorialFragment
        _navigationProps.value = NavigationProps(navigationId, null)
    }
}


