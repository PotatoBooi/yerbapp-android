package com.polsl.yerbapp.di

import com.polsl.yerbapp.data.UsersRepository
import com.polsl.yerbapp.data.network.ConnectivityInterceptor
import com.polsl.yerbapp.data.network.GraphqlService
import com.polsl.yerbapp.data.network.RetrofitService
import org.koin.dsl.module

val repositoriesModule = module {
    single { ConnectivityInterceptor(get()) }
    single { RetrofitService(get()) }
    single { GraphqlService(get()) }
    single { UsersRepository(get(), get()) }
}