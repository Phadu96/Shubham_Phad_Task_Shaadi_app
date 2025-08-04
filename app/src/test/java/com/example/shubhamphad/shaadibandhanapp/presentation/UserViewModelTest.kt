package com.example.shubhamphad.shaadibandhanapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.shubhamphad.shaadibandhanapp.domain.model.Location
import com.example.shubhamphad.shaadibandhanapp.domain.model.User
import com.example.shubhamphad.shaadibandhanapp.domain.usecases.UpdateUserStatusUseCase
import com.example.shubhamphad.shaadibandhanapp.domain.usecases.UserDataUseCase
import com.example.shubhamphad.shaadibandhanapp.presentation.ui.user.UserViewModel
import com.example.shubhamphad.shaadibandhanapp.utils.UiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var userDataUseCase: UserDataUseCase
    private lateinit var updateUserStatusUseCase: UpdateUserStatusUseCase
    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        userDataUseCase = mockk()
        updateUserStatusUseCase = mockk(relaxed = true)
        viewModel = UserViewModel(userDataUseCase, updateUserStatusUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadInitialUsers should emit Success state when users are fetched`() = runTest {
        // Arrange
        val users = listOf(
            User(id = "1", name = "John", gender = "Male", email = "john@gmail.com", age = 30, phone = "123", pictureUrl = "", location = mockLocation(), nationality = "IN")
        )
        coEvery { userDataUseCase(1, 10) } returns Result.success(users)

        val observed = mutableListOf<UiState<List<User>>>()
        val observer = Observer<UiState<List<User>>> { observed.add(it) }
        viewModel.userList.observeForever(observer)

        // Act
        viewModel.loadInitialUsers()
        advanceUntilIdle()

        // Assert
        assert(observed.any { it is UiState.Success && it.data == users })

        viewModel.userList.removeObserver(observer)
    }

    @Test
    fun `loadInitialUsers should post Error when usecase fails`() = runTest {
        coEvery { userDataUseCase(1, 10) } returns Result.failure(Exception("Network error"))
        var result: UiState<List<User>>? = null
        viewModel.userList.observeForever { result = it }
        viewModel.loadInitialUsers()
        advanceUntilIdle()

        assertTrue(result is UiState.Error)
        assertEquals("Failed to load users", (result as UiState.Error).message)
    }

    @Test
    fun `updateUserStatus should update the correct user`() = runTest {
        // Given
        val users = listOf(User(id = "1", name = "John", gender = "male", email = "a@a.com", age = 30, phone = "123", pictureUrl = "", location = mockLocation(), nationality = "IN"))
        coEvery { userDataUseCase(1, 10) } returns Result.success(users)
        var result: UiState<List<User>>? = null
        viewModel.userList.observeForever { result = it }
        viewModel.loadInitialUsers()
        advanceUntilIdle()

        // When
        viewModel.updateUserStatus("1", "accepted")
        advanceUntilIdle()

        // Then
        val updated = (result as UiState.Success).data.find { it.id == "1" }
        assertEquals("accepted", updated?.status)
        coVerify { updateUserStatusUseCase("1", "accepted") }
    }

    private fun mockLocation() = com.example.shubhamphad.shaadibandhanapp.domain.model.Location(
        street = "123 Street",
        city = "City",
        state = "State",
        country = "Country",
        postcode = "123456"
    )
}