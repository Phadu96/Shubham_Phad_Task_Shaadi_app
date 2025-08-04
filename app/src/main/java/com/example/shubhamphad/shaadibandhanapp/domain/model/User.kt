package com.example.shubhamphad.shaadibandhanapp.domain.model

data class User(
    val id: String,
    val name: String,
    val gender: String?,
    val email: String?,
    val age: Int?,
    val phone: String?,
    val pictureUrl: String?,
    val location: Location,
    val nationality: String?,
    val status: String = ""
)

data class Location(
    val street: String,
    val city: String?,
    val state: String?,
    val country: String?,
    val postcode: String?
)
