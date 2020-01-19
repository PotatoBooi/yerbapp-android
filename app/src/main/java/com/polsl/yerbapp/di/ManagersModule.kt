package com.polsl.yerbapp.di

import com.polsl.yerbapp.data.device.SharedPreferencesManager
import org.koin.dsl.module

val managersModule = module {
    single { SharedPreferencesManager(get()) }
}
