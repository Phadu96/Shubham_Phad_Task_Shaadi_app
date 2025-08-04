package com.example.shubhamphad.shaadibandhanapp.domain.repository

import com.example.shubhamphad.shaadibandhanapp.domain.model.User

interface UserRepository {
    suspend fun getUsers(page: Int, size: Int): Result<List<User>>
    suspend fun updateUserStatus(id: String, newStatus: String)
    suspend fun getUnsyncedUsers(): List<User>
    suspend fun syncUserStatus(user: User)
}