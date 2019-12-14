package com.polsl.yerbapp.di

import com.polsl.yerbapp.presentation.ui.explore.ExploreViewModel
import com.polsl.yerbapp.presentation.ui.profile.ProfileSharedViewModel
import com.polsl.yerbapp.presentation.ui.profile.ProfileViewModel
import com.polsl.yerbapp.presentation.ui.profile.login.LoginViewModel
import com.polsl.yerbapp.presentation.ui.profile.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { ProfileViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ProfileSharedViewModel() }
    viewModel { ExploreViewModel(get()) }
}