package com.polsl.yerbapp.di

import com.polsl.yerbapp.presentation.usecases.CurrentUserCase
import com.polsl.yerbapp.presentation.usecases.LoginUserCase
import com.polsl.yerbapp.presentation.usecases.RegisterUserCase
import com.polsl.yerbapp.presentation.usecases.ProductsCase

import org.koin.dsl.module

val useCasesModule = module {
    single { LoginUserCase(get()) }
    single { RegisterUserCase(get()) }
    single { CurrentUserCase(get()) }
    single { ProductsCase (get())}
}