package com.example.shubhamphad.shaadibandhanapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey val id: String,
    val name: String,
    val gender: String?,
    val email: String?,
    val age: Int?,
    val phone: String?,
    val pictureUrl: String?,
    val street: String,
    val city: String?,
    val state: String?,
    val country: String?,
    val postcode: String?,
    val nationality: String?,
    val status: String = "",
    val needSync: Boolean = false
)