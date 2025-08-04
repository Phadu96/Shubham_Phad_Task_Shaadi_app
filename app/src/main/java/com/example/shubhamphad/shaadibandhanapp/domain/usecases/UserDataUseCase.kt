package com.example.shubhamphad.shaadibandhanapp.domain.usecases

import com.example.shubhamphad.shaadibandhanapp.domain.model.User
import com.example.shubhamphad.shaadibandhanapp.domain.repository.UserRepository

class UserDataUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(page: Int, size: Int): Result<List<User>> {
        return repository.getUsers(page, size)
    }
}