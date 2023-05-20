package com.challenge.github.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.challenge.github.core.NetworkResult
import com.challenge.github.core.repository.GitHubRepository
import com.challenge.github.data.userDetailResponse
import com.challenge.github.data.userLogin
import com.challenge.github.data.userRepositoryList
import com.challenge.github.model.UserDetail
import com.challenge.github.model.UserRepository
import com.challenge.github.model.response.UserDetailResponse
import com.challenge.github.model.response.UserRepositoryResponse
import com.challenge.github.model.response.toUserDetail
import com.challenge.github.ui.user.UserViewModel
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
class UserViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var viewModel: UserViewModel
    private lateinit var repository: GitHubRepository
    private lateinit var usersObserver: Observer<UserDetail>
    private lateinit var repositoriesObserver: Observer<List<UserRepository>>
    private lateinit var isLoadingObserver: Observer<Boolean>
    private lateinit var errorObserver: Observer<String>

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        usersObserver = mockk(relaxed = true)
        repositoriesObserver = mockk(relaxed = true)
        isLoadingObserver = mockk(relaxed = true)
        errorObserver = mockk(relaxed = true)
        viewModel = UserViewModel(repository)
        viewModel.user.observeForever(usersObserver)
        viewModel.repositories.observeForever(repositoriesObserver)
        viewModel.isLoading.observeForever(isLoadingObserver)
        viewModel.error.observeForever(errorObserver)

        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `getUserDetail should update user and isLoading values when network request is successful`() =
        testScope.runTest {
            // Arrange
            val user = userDetailResponse
            val successResult = NetworkResult.Success(user)
            coEvery { repository.getUser(userLogin) } returns successResult

            // Act
            viewModel.getUserDetail(userLogin)
            val result = user.toUserDetail()

            // Assert
            coVerify { repository.getUser(userLogin) }
            verify { isLoadingObserver.onChanged(true) }
            verify { usersObserver.onChanged(result) }
            verify { isLoadingObserver.onChanged(false) }
        }

    @Test
    fun `getUserDetail should update error and isLoading values when network request returns an error`() =
        testScope.runTest {
            // Arrange
            val errorMessage = "Network error"
            val errorResult: NetworkResult<UserDetailResponse> = NetworkResult.Error(errorMessage)
            coEvery { repository.getUser(userLogin) } returns errorResult

            // Act
            viewModel.getUserDetail(userLogin)

            // Assert
            coVerify { repository.getUser(userLogin) }
            verify { isLoadingObserver.onChanged(true) }
            verify { errorObserver.onChanged(errorMessage) }
            verify { isLoadingObserver.onChanged(false) }
        }

    @Test
    fun `getUserRepositories should update repositories and isLoading values when network request is successful`() =
        testScope.runTest {
            // Arrange
            val repositories = userRepositoryList
            val successResult = NetworkResult.Success(repositories)
            coEvery { repository.getUserRepositories(userLogin) } returns successResult

            // Act
            viewModel.getUserRepositories(userLogin)
            val result = repositories.map { userResponse -> userResponse.toUserRepository() }

            // Assert
            coVerify { repository.getUserRepositories(userLogin) }
            verify { repositoriesObserver.onChanged(result) }
            verify { isLoadingObserver.onChanged(false) }
        }

    @Test
    fun `getUserRepositories should update repositories and isLoading values when network request returns an error`() =
        testScope.runTest {
            // Arrange
            val errorMessage = "Network error"
            val errorResult: NetworkResult<List<UserRepositoryResponse>> = NetworkResult.Error(errorMessage)
            coEvery { repository.getUserRepositories(userLogin) } returns errorResult

            // Act
            viewModel.getUserRepositories(userLogin)

            // Assert
            coVerify { repository.getUserRepositories(userLogin) }
            verify { errorObserver.onChanged(errorMessage) }
            verify { isLoadingObserver.onChanged(false) }
        }

}