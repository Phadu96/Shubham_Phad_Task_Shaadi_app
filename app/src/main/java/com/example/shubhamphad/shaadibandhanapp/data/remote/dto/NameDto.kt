package com.example.shubhamphad.shaadibandhanapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NameDto(
    @SerializedName("title") val title: String? = null,
    @SerializedName("first") val first: String? = null,
    @SerializedName("last") val last: String? = null
)
