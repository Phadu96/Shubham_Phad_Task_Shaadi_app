package com.example.shubhamphad.shaadibandhanapp.di

import com.example.shubhamphad.shaadibandhanapp.data.repository.UserRepositoryImpl
import com.example.shubhamphad.shaadibandhanapp.domain.repository.UserRepository
import org.koin.dsl.module


val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get(), get(),get()) }
}
