package com.example.shubhamphad.shaadibandhanapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Dob(
    @SerializedName("date") val date: String? = null,
    @SerializedName("age") val age: Int? = null
)

data class Registered(
    @SerializedName("date") val date: String? = null,
    @SerializedName("age") val age: Int? = null
)
