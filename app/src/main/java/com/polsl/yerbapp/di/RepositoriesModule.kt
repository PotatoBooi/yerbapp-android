package com.polsl.yerbapp.di

import com.polsl.yerbapp.data.UsersRepository
import com.polsl.yerbapp.data.ProductsRepository
import com.polsl.yerbapp.data.network.ApolloClientFactory
import com.polsl.yerbapp.data.network.ConnectivityInterceptor
import com.polsl.yerbapp.data.network.RetrofitService
import org.koin.dsl.module

val repositoriesModule = module {
    single { ConnectivityInterceptor(get()) }
    single { RetrofitService(get()) }
    single { ApolloClientFactory(get(), get()) }
    single { UsersRepository(get(), get()) }
    single { ProductsRepository(get())}
}