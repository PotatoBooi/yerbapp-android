package com.polsl.yerbapp.di

import com.polsl.yerbapp.presentation.ui.explore.ExploreViewModel
import com.polsl.yerbapp.presentation.ui.explore.ProductsSharedViewModel
import com.polsl.yerbapp.presentation.ui.explore.add_product.AddProductViewModel
import com.polsl.yerbapp.presentation.ui.explore.product_preview.ProductPreviewViewModel
import com.polsl.yerbapp.presentation.ui.profile.ProfileSharedViewModel
import com.polsl.yerbapp.presentation.ui.profile.ProfileViewModel
import com.polsl.yerbapp.presentation.ui.profile.login.LoginViewModel
import com.polsl.yerbapp.presentation.ui.profile.register.RegisterViewModel
import com.polsl.yerbapp.presentation.ui.training.TrainingViewModel
import com.polsl.yerbapp.presentation.ui.training.tutorial.TutorialViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { ProfileViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ProfileSharedViewModel() }
    viewModel { ExploreViewModel(get(), get()) }
    viewModel { (productId: String) -> ProductPreviewViewModel(productId, get(), get()) }
    viewModel { AddProductViewModel(get()) }
    viewModel { TutorialViewModel() }
    viewModel { TrainingViewModel() }
    viewModel { ProductsSharedViewModel() }
}