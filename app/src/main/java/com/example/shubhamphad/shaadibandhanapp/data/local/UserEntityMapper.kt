package com.example.shubhamphad.shaadibandhanapp.data.local

import com.example.shubhamphad.shaadibandhanapp.domain.model.Location
import com.example.shubhamphad.shaadibandhanapp.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id,
        name,
        gender,
        email,
        age,
        phone,
        pictureUrl,
        Location(street, city, state, country, postcode),
        nationality,
        status
    )
}