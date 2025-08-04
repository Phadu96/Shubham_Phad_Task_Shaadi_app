package com.example.shubhamphad.shaadibandhanapp

import android.app.Application
import com.example.shubhamphad.shaadibandhanapp.di.databaseModule
import com.example.shubhamphad.shaadibandhanapp.di.networkConnectionModule
import com.example.shubhamphad.shaadibandhanapp.di.networkModule
import com.example.shubhamphad.shaadibandhanapp.di.repositoryModule
import com.example.shubhamphad.shaadibandhanapp.di.useCaseModule
import com.example.shubhamphad.shaadibandhanapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShaadiBandhanApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ShaadiBandhanApp)
            modules(
                networkModule,
                databaseModule,
                networkConnectionModule,
                repositoryModule,
                viewModelModule,
                useCaseModule
            )
        }
    }
}