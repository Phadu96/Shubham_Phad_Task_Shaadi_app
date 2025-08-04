package com.example.shubhamphad.shaadibandhanapp.di

import android.app.Application
import androidx.room.Room
import com.example.shubhamphad.shaadibandhanapp.data.local.ShaadiBandhanDatabase
import com.example.shubhamphad.shaadibandhanapp.data.local.UserDao
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get<Application>(),
            ShaadiBandhanDatabase::class.java,
            "ShaadiBandhan"
        ).build()
    }

    single<UserDao> { get<ShaadiBandhanDatabase>().userDao() }
}