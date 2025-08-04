package com.example.shubhamphad.shaadibandhanapp.di

import com.example.shubhamphad.shaadibandhanapp.data.remote.api.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private fun provideHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .connectTimeout(30, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.MINUTES)
        .writeTimeout(30, TimeUnit.MINUTES)
        .build()
}

private fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://randomuser.me")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

val networkModule = module {
    single { provideHttpClient() }
    single { provideRetrofit(get()) }
    single<UserApi> { get<Retrofit>().create(UserApi::class.java) }
}