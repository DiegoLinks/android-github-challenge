package com.challenge.github.repository

import com.challenge.github.core.NetworkResult
import com.challenge.github.core.network.GitHubApiService
import com.challenge.github.core.repository.GitHubRepositoryImpl
import com.challenge.github.core.util.Message
import com.challenge.github.data.userDetailResponse
import com.challenge.github.data.userLogin
import com.challenge.github.data.userRepositoryList
import com.challenge.github.data.userResponseList
import com.challenge.github.model.User
import com.challenge.github.model.response.UserDetailResponse
import com.challenge.github.model.response.UserRepositoryResponse
import com.challenge.github.model.response.UserResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import okio.IOException
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GitHubRepositoryImplTest {

    private lateinit var gitHubApiService: GitHubApiService
    private lateinit var repository: GitHubRepositoryImpl

    @Before
    fun setup() {
        gitHubApiService = mockk(relaxed = true)
        repository = GitHubRepositoryImpl(gitHubApiService)
    }

    @Test
    fun `getUserList should return Success with user list when API call is successful`() =
        runBlocking {
            // Arrange
            val userList = userResponseList
            coEvery { gitHubApiService.getUsers() } returns Response.success(userList)

            // Act
            val expected = NetworkResult.Success(userList)
            val result = repository.getUserList()

            // Assert
            coVerify { gitHubApiService.getUsers() }
            assertEquals(expected.data, result.data)
        }

    @Test
    fun `getUserList should return Error with error message when API call is unsuccessful`() =
        runBlocking {
            // Arrange
            val errorMessage =
                "Aconteceu um erro inesperado. Por favor, tente novamente mais tarde."
            val errorResponse =
                Response.error<List<UserResponse>>(404, ResponseBody.create(null, errorMessage))
            coEvery { gitHubApiService.getUsers() } returns errorResponse

            // Act
            val result = repository.getUserList()
            val errorResult: NetworkResult<List<UserResponse>> = NetworkResult.Error(errorMessage)

            // Assert
            coVerify { gitHubApiService.getUsers() }
            assertEquals(errorResult.data, result.data)
            assertEquals(errorResult.message, result.message)
        }

    @Test
    fun `getUser should return Success with user detail when API call is successful`() =
        runBlocking {
            // Arrange
            val userDetail = userDetailResponse
            coEvery { gitHubApiService.getUser(userLogin) } returns Response.success(userDetail)

            // Act
            val expected = NetworkResult.Success(userDetail)
            val result = repository.getUser(userLogin)

            // Assert
            coVerify { gitHubApiService.getUser(userLogin) }
            assertEquals(expected.data, result.data)
        }

    @Test
    fun `getUser should return Error with error message when API call is unsuccessful`() =
        runBlocking {
            // Arrange
            val userLogin = "username"
            val errorMessage =
                "Aconteceu um erro inesperado. Por favor, tente novamente mais tarde."
            val errorResponse =
                Response.error<UserDetailResponse>(404, ResponseBody.create(null, errorMessage))
            coEvery { gitHubApiService.getUser(userLogin) } returns errorResponse

            // Act
            val result = repository.getUser(userLogin)
            val errorResult: NetworkResult<UserDetailResponse> = NetworkResult.Error(errorMessage)

            // Assert
            coVerify { gitHubApiService.getUser(userLogin) }
            assertEquals(errorResult.data, result.data)
            assertEquals(errorResult.message, result.message)
        }

    @Test
    fun `getUserRepositories should return Success with user repositories when API call is successful`() =
        runBlocking {
            // Arrange
            val repositories = userRepositoryList
            coEvery { gitHubApiService.getUserRepositories(userLogin) } returns Response.success(
                repositories
            )

            // Act
            val expected = NetworkResult.Success(repositories)
            val result = repository.getUserRepositories(userLogin)

            // Assert
            coVerify { gitHubApiService.getUserRepositories(userLogin) }
            assertEquals(expected.data, result.data)
        }

    @Test
    fun `getUserRepositories should return Error with error message when API call is unsuccessful`() =
        runBlocking {
            // Arrange
            val userLogin = "username"
            val errorMessage =
                "Aconteceu um erro inesperado. Por favor, tente novamente mais tarde."
            val errorResponse = Response.error<List<UserRepositoryResponse>>(
                404,
                ResponseBody.create(null, errorMessage)
            )
            coEvery { gitHubApiService.getUserRepositories(userLogin) } returns errorResponse

            // Act
            val result = repository.getUserRepositories(userLogin)
            val errorResult: NetworkResult<List<UserRepositoryResponse>> =
                NetworkResult.Error(errorMessage)

            // Assert
            coVerify { gitHubApiService.getUserRepositories(userLogin) }
            assertEquals(errorResult.data, result.data)
            assertEquals(errorResult.message, result.message)
        }

    @Test
    fun `handleApi should return Success with response body when API call is successful`() =
        runBlocking {
            // Arrange
            val responseBody = "Response Body"
            val response = Response.success(responseBody)

            // Act
            val expected = NetworkResult.Success(responseBody)
            val result = repository.handleApi { response }

            // Assert
            assertEquals(expected.data, result.data)
        }

    @Test
    fun `handleApi should return Error with error message when API call is unsuccessful`() =
        runBlocking {
            // Arrange
            val errorMessage =
                "Aconteceu um erro inesperado. Por favor, tente novamente mais tarde."
            val errorResponse = Response.error<String>(404, ResponseBody.create(null, errorMessage))

            // Act
            val result = repository.handleApi { errorResponse }
            val errorResult: NetworkResult<String> = NetworkResult.Error(errorMessage)

            // Assert
            assertEquals(errorResult.data, result.data)
            assertEquals(errorResult.message, result.message)
        }

    @Test
    fun `handleApi should return Error with generic error message when an IOException occurs`() =
        runBlocking {
            // Arrange
            val expectedErrorMessage = Message.GENERIC_ERROR_MESSAGE

            // Act
            val result: NetworkResult<User> = repository.handleApi { throw IOException() }

            // Assert
            assertEquals(expectedErrorMessage, result.message)
        }

    @Test
    fun `handleApi should return Error with generic error message when a Throwable occurs`() =
        runBlocking {
            // Arrange
            val expectedErrorMessage = Message.GENERIC_ERROR_MESSAGE

            // Act
            val result: NetworkResult<User> = repository.handleApi { throw Throwable() }

            // Assert
            assertEquals(expectedErrorMessage, result.message)
        }
}