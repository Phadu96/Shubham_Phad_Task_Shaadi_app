package com.example.shubhamphad.shaadibandhanapp.domain.usecases

import com.example.shubhamphad.shaadibandhanapp.domain.repository.UserRepository

class UpdateUserStatusUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: String, status: String) =
        repository.updateUserStatus(userId, status)
}