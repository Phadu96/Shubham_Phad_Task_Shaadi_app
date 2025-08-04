package com.example.shubhamphad.shaadibandhanapp.di

import com.example.shubhamphad.shaadibandhanapp.data.repository.NetworkConnectionImpl
import org.koin.dsl.module

val networkConnectionModule = module {
    single { NetworkConnectionImpl(get()) }
}