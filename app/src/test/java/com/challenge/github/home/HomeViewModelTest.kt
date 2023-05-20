package com.challenge.github.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.challenge.github.core.NetworkResult
import com.challenge.github.core.repository.GitHubRepository
import com.challenge.github.data.user1
import com.challenge.github.data.user2
import com.challenge.github.data.user3
import com.challenge.github.data.userResponseList
import com.challenge.github.model.User
import com.challenge.github.model.response.UserResponse
import com.challenge.github.model.response.toUser
import com.challenge.github.ui.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: GitHubRepository
    private lateinit var usersObserver: Observer<List<User>>
    private lateinit var isLoadingObserver: Observer<Boolean>
    private lateinit var errorObserver: Observer<String>

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        usersObserver = mockk(relaxed = true)
        isLoadingObserver = mockk(relaxed = true)
        errorObserver = mockk(relaxed = true)

        viewModel = HomeViewModel(repository)
        viewModel.users.observeForever(usersObserver)
        viewModel.isLoading.observeForever(isLoadingObserver)
        viewModel.error.observeForever(errorObserver)

        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `getUserList should update users and isLoading values when network request is successful`() =
        testScope.runTest {
            // Arrange
            val userList = userResponseList
            val successResult = NetworkResult.Success(userList)
            coEvery { repository.getUserList() } returns successResult

            // Act
            viewModel.getUserList()
            val result = userList.map { userResponse -> userResponse.toUser() }

            // Assert
            coVerify { repository.getUserList() }
            verify { isLoadingObserver.onChanged(true) }
            verify { usersObserver.onChanged(result) }
            verify { isLoadingObserver.onChanged(false) }
        }


    @Test
    fun `getUserList should update error and isLoading values when network request returns an error`() =
        testScope.runTest {
            // Arrange
            val errorMessage = "Network error"
            val errorResult: NetworkResult<List<UserResponse>> = NetworkResult.Error(errorMessage)
            coEvery { repository.getUserList() } returns errorResult

            // Act
            viewModel.getUserList()

            // Assert
            coVerify { repository.getUserList() }
            verify { isLoadingObserver.onChanged(true) }
            verify { errorObserver.onChanged(errorMessage) }
            verify { isLoadingObserver.onChanged(false) }
        }

    @Test
    fun `updateListWithSearchText should update users value with filtered list based on search text`() {
        // Arrange
        val searchText = "john"
        val userList =
            listOf(user1, user2, user3)//user1 and user2 have "john" in their name, user 2 does not.
        viewModel.receivedUserList = userList

        // Act
        viewModel.updateListWithSearchText(searchText)

        // Assert
        verify { usersObserver.onChanged(listOf(user1, user3)) }
    }
}