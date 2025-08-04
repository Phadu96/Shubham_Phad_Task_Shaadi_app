package com.example.shubhamphad.shaadibandhanapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Id(
    @SerializedName("name") val name: String? = null,
    @SerializedName("value") val value: String? = null
)
