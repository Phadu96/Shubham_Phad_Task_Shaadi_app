package com.example.shubhamphad.shaadibandhanapp.di

import com.example.shubhamphad.shaadibandhanapp.presentation.ui.user.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { UserViewModel(get(),get()) }
}