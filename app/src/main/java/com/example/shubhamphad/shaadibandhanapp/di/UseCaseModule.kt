package com.example.shubhamphad.shaadibandhanapp.di

import com.example.shubhamphad.shaadibandhanapp.domain.usecases.UpdateUserStatusUseCase
import com.example.shubhamphad.shaadibandhanapp.domain.usecases.UserDataUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { UserDataUseCase(get()) }
    single { UpdateUserStatusUseCase(get()) }

}