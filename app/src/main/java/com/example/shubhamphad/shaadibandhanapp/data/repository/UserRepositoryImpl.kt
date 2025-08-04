package com.example.shubhamphad.shaadibandhanapp.data.repository

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.shubhamphad.shaadibandhanapp.data.local.UserDao
import com.example.shubhamphad.shaadibandhanapp.data.local.UserEntity
import com.example.shubhamphad.shaadibandhanapp.data.local.toDomain
import com.example.shubhamphad.shaadibandhanapp.data.remote.mapper.toEntity
import com.example.shubhamphad.shaadibandhanapp.domain.model.User
import com.example.shubhamphad.shaadibandhanapp.domain.repository.UserRepository
import com.example.shubhamphad.shaadibandhanapp.data.remote.api.UserApi
import com.example.shubhamphad.shaadibandhanapp.data.sync.SyncStatusWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.collections.isNotEmpty
import kotlin.collections.map

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val networkConnectionImpl: NetworkConnectionImpl,
    private val userDao: UserDao,
    private val appContext: Context

) : UserRepository {
    override suspend fun getUsers(page: Int, size: Int): Result<List<User>> {
        return try {
            if (networkConnectionImpl.isNetworkConnected()) {
                val response = userApi.getUser(page, size)
                if (response.isSuccessful && response.body() != null) {
                    val remoteUsers = response.body()?.results?.map { it.toEntity() } ?: emptyList()
                    Log.d("UserRepo", "API response results count: ${remoteUsers.size}")

                    val remoteUsersUpdated = withContext(Dispatchers.IO) {
                        val localUsers = userDao.getAllUsers().associateBy { it.id }

                        remoteUsers.map { remote ->
                            localUsers[remote.id]?.let { local ->
                                remote.copy(
                                    status = local.status.ifEmpty { remote.status },
                                    needSync = local.needSync
                                )
                            } ?: remote
                        }.also { merged ->
                            userDao.insertUser(merged)
                        }
                    }

                    Result.success(remoteUsersUpdated.map { it.toDomain() })
                } else {
                    Result.failure(Exception("API Error"))
                }
            } else {
                val localPage = getLocalUsers(page, size)
                Result.success(localPage)
            }
        } catch (e: Exception) {
            val localFallback = getLocalUsers(page, size)
            if (localFallback.isNotEmpty()) {
                Result.success(localFallback)
            } else {
                Result.failure(e)
            }
        }
    }

    private suspend fun getLocalUsers(page: Int, size: Int): List<User> = withContext(Dispatchers.IO) {
        val all = userDao.getAllUsers().map { it.toDomain() }
        val fromIndex = (page - 1) * size
        val toIndex = (fromIndex + size).coerceAtMost(all.size)
        if (fromIndex >= all.size) emptyList() else all.subList(fromIndex, toIndex)
    }

    override suspend fun updateUserStatus(id: String, newStatus: String) {
        withContext(Dispatchers.IO) {
            userDao.updateStatus(id, newStatus)
            enqueueSyncWork()
        }
    }

    override suspend fun getUnsyncedUsers(): List<User> {
        return userDao.getUnsyncedUsers().map { it.toDomain() }
    }

    override suspend fun syncUserStatus(user: User) {
        // Api not available for sync, marking as success for now
        withContext(Dispatchers.IO) {
            userDao.markSynced(user.id)
        }
    }

    private fun enqueueSyncWork() {
        val work = OneTimeWorkRequestBuilder<SyncStatusWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(appContext)
            .enqueueUniqueWork("sync_status_worker", ExistingWorkPolicy.KEEP, work)
    }
}