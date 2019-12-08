package com.polsl.yerbapp

import android.app.Application
import com.polsl.yerbapp.presentation.di.managersModule
import com.polsl.yerbapp.presentation.di.repositoriesModule
import com.polsl.yerbapp.presentation.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class YerbApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@YerbApp)
            modules(listOf(managersModule, repositoriesModule, viewModelsModule))
        }
    }
}