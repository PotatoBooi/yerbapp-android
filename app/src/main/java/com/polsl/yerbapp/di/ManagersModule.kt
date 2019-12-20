package com.polsl.yerbapp.di

import com.polsl.yerbapp.data.device.SharedPreferencesManager
import com.polsl.yerbapp.presentation.ui.explore.adapters.ProductsDataSource
import com.polsl.yerbapp.presentation.ui.explore.adapters.ProductsDataSourceFactory
import org.koin.dsl.module

val managersModule = module {
    single { SharedPreferencesManager(get()) }
    single { ProductsDataSourceFactory(get())}
    single { ProductsDataSource(get())}
}
