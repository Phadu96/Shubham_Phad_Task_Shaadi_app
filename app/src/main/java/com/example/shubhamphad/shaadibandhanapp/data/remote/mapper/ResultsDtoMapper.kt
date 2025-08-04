package com.example.shubhamphad.shaadibandhanapp.data.remote.mapper

import com.example.shubhamphad.shaadibandhanapp.data.local.UserEntity
import com.example.shubhamphad.shaadibandhanapp.data.remote.dto.ResultsDto

fun ResultsDto.toEntity(): UserEntity {
    return UserEntity(
        login?.uuid.orEmpty(),
        listOfNotNull(name?.first, name?.last).joinToString(" "),
        gender,
        email,
        dob?.age,
        phone,
        picture?.large,
        listOfNotNull(location?.street?.number?.toString(), location?.street?.name).joinToString(" "),
        location?.city,
        location?.state,
        location?.country,
        location?.postcode,
        nat,
        ""
    )
}