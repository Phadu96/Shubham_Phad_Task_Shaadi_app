package com.example.shubhamphad.shaadibandhanapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: List<UserEntity>)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("UPDATE users SET status = :status, needSync = 1 WHERE id = :id")
    suspend fun updateStatus(id: String, status: String)

    @Query("SELECT * FROM users WHERE needSync = 1")
    suspend fun getUnsyncedUsers(): List<UserEntity>

    @Query("UPDATE users SET needSync = 0 WHERE id = :id")
    suspend fun markSynced(id: String)

    @Delete
    suspend fun deleteUser(user: UserEntity)
}