package com.example.shubhamphad.shaadibandhanapp.presentation.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shubhamphad.shaadibandhanapp.domain.model.User
import com.example.shubhamphad.shaadibandhanapp.domain.usecases.UpdateUserStatusUseCase
import com.example.shubhamphad.shaadibandhanapp.domain.usecases.UserDataUseCase
import com.example.shubhamphad.shaadibandhanapp.utils.UiState
import kotlinx.coroutines.launch

class UserViewModel(
    private val userDataUseCase: UserDataUseCase,
    private val updateUserStatusUseCase: UpdateUserStatusUseCase
) : ViewModel() {
    private val _userList = MutableLiveData<UiState<List<User>>>()
    val userList: LiveData<UiState<List<User>>> = _userList

    private val allUsers = mutableListOf<User>()
    private var currentPage = 1
    private val pageSize = 10
    var hasMorePages = true
        private set

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadInitialUsers() {
        currentPage = 1
        hasMorePages = true
        allUsers.clear()
        fetchUsers()
    }

    fun loadNextPage() {
        if (_isLoading.value == true || !hasMorePages) return
        currentPage += 1
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            if (currentPage == 1) _userList.value = UiState.Loading

            val result = userDataUseCase(currentPage, pageSize)

            result.fold(
                onSuccess = { data ->
                    hasMorePages = data.size == pageSize
                    allUsers += data
                    //Log.d("UserViewModel", "Fetched users size: ${data.size}")


                    if (allUsers.isEmpty()) {
                        _userList.value = UiState.Empty
                    } else {
                        _userList.value = UiState.Success(allUsers)
                    }
                },
                onFailure = {
                    _userList.value = UiState.Error("Failed to load users")
                }
            )
            _isLoading.value = false
        }
    }

    fun updateUserStatus(id: String, newStatus: String) {
        viewModelScope.launch {
            updateUserStatusUseCase(id, newStatus)

            allUsers.replaceAll { user ->
                if (user.id == id) user.copy(status = newStatus) else user
            }
            _userList.value = UiState.Success(allUsers)
        }
    }
}
