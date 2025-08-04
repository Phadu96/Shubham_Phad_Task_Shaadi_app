package com.example.shubhamphad.shaadibandhanapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)

abstract class ShaadiBandhanDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}