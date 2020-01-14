package com.polsl.yerbapp.di

import com.polsl.yerbapp.data.repos.UsersRepository
import com.polsl.yerbapp.data.repos.ProductsRepository
import com.polsl.yerbapp.data.network.ApolloClientFactory
import com.polsl.yerbapp.data.network.ConnectivityInterceptor
import com.polsl.yerbapp.data.network.RetrofitService
import com.polsl.yerbapp.data.repos.ManufacturersRepository
import com.polsl.yerbapp.data.repos.TypesRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single { ConnectivityInterceptor(get()) }
    single { RetrofitService(get(), get()) }
    single { ApolloClientFactory(get(), get()) }
    single { UsersRepository(get(), get(), get()) }
    single { ProductsRepository(get(), get(), get()) }
    single { ManufacturersRepository (get())}
    single { TypesRepository (get())}
}