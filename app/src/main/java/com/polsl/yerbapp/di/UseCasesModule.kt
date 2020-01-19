package com.polsl.yerbapp.di

import com.polsl.yerbapp.presentation.usecases.*

import org.koin.dsl.module

val useCasesModule = module {
    single { LoginUserCase(get()) }
    single { RegisterUserCase(get()) }
    single { CurrentUserCase(get()) }
    single { ProductsCase(get()) }
    single { ProductCase(get(), get(), get()) }
}