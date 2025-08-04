package com.example.shubhamphad.shaadibandhanapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("street") val street: Street? = null,
    @SerializedName("city") val city: String? = null,
    @SerializedName("state") val state: String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("postcode") val postcode: String? = null,
    @SerializedName("coordinates") val coordinates: Coordinates? = null,
    @SerializedName("timezone") val timezone: Timezone? = null
)

data class Street(
    @SerializedName("number") val number: Int? = null,
    @SerializedName("name") val name: String? = null
)

data class Coordinates(
    @SerializedName("latitude") val latitude: String? = null,
    @SerializedName("longitude") val longitude: String? = null
)

data class Timezone(
    @SerializedName("offset") val offset: String? = null,
    @SerializedName("description") val description: String? = null
)

