package com.example.shubhamphad.shaadibandhanapp.data.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.shubhamphad.shaadibandhanapp.ShaadiBandhanApp
import com.example.shubhamphad.shaadibandhanapp.domain.repository.UserRepository
import org.koin.java.KoinJavaComponent.inject

class SyncStatusWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val userRepository: UserRepository by inject(UserRepository::class.java)

    override suspend fun doWork(): Result {
        val unsyncedUsers = userRepository.getUnsyncedUsers()

        for (user in unsyncedUsers) {
            try {
                userRepository.syncUserStatus(user)
            } catch (e: Exception) {

            }
        }

        return Result.success()
    }
}