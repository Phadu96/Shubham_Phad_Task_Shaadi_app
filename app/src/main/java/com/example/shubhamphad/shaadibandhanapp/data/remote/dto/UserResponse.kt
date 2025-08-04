package com.example.shubhamphad.shaadibandhanapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("results") var results: ArrayList<ResultsDto> = arrayListOf(),
    @SerializedName("info") var info: Info? = Info()
)

data class Info(
    @SerializedName("seed") var seed: String? = null,
    @SerializedName("results") var results: Int? = null,
    @SerializedName("page") var page: Int? = null,
    @SerializedName("version") var version: String? = null
)