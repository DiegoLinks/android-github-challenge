package com.challenge.github.core.repository

import com.challenge.github.core.NetworkResult
import com.challenge.github.core.network.GitHubApiService
import com.challenge.github.core.util.ErrorResponseConvert.Companion.convertErrorBody
import com.challenge.github.core.util.Message.Companion.GENERIC_ERROR_MESSAGE
import com.challenge.github.model.response.UserDetailResponse
import com.challenge.github.model.response.UserRepositoryResponse
import com.challenge.github.model.response.UserResponse
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException
import retrofit2.Response

class GitHubRepositoryImpl @Inject constructor(
    private val gitHubApiService: GitHubApiService
) : GitHubRepository {

    override suspend fun getUserList(): NetworkResult<List<UserResponse>> {
        return handleApi { gitHubApiService.getUsers() }
    }

    override suspend fun getUser(userLogin: String): NetworkResult<UserDetailResponse> {
        return handleApi { gitHubApiService.getUser(userLogin) }
    }

    override suspend fun getUserRepositories(userLogin: String): NetworkResult<List<UserRepositoryResponse>> {
        return handleApi { gitHubApiService.getUserRepositories(userLogin) }
    }

    suspend fun <T : Any> handleApi(
        execute: suspend () -> Response<T>
    ): NetworkResult<T> {
        return try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                NetworkResult.Success(body)
            } else {
                val errorMessage =
                    convertErrorBody(response.errorBody())?.message ?: GENERIC_ERROR_MESSAGE
                NetworkResult.Error(errorMessage = errorMessage)
            }
        } catch (e: HttpException) {
            NetworkResult.Error(errorMessage = e.message())
        } catch (e: IOException) {
            NetworkResult.Error(errorMessage = e.message ?: GENERIC_ERROR_MESSAGE)
        } catch (e: Throwable) {
            NetworkResult.Error(errorMessage = e.message ?: GENERIC_ERROR_MESSAGE)
        }
    }
}