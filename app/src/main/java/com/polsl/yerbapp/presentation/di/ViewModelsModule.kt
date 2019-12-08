package com.polsl.yerbapp.presentation.di

import com.polsl.yerbapp.presentation.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { ProfileViewModel() }
}