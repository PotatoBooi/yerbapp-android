package com.polsl.yerbapp.di

import com.polsl.yerbapp.presentation.usecases.GetCurrentUserCase
import com.polsl.yerbapp.presentation.usecases.LoginUserCase
import com.polsl.yerbapp.presentation.usecases.RegisterUserCase
import org.koin.dsl.module

val useCasesModule = module {
    single { LoginUserCase(get()) }
    single { RegisterUserCase(get()) }
    single { GetCurrentUserCase(get()) }
}