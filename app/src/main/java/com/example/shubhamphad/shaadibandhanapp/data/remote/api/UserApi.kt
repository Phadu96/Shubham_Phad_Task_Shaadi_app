package com.example.shubhamphad.shaadibandhanapp.data.remote.api


import com.example.shubhamphad.shaadibandhanapp.data.remote.dto.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("/api/")
    suspend fun getUser(
        @Query("page") page: Int ,
        @Query("results") results: Int
    ): Response<UserResponse>

}