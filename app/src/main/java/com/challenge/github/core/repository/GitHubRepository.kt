package com.challenge.github.core.repository

import com.challenge.github.core.NetworkResult
import com.challenge.github.model.response.UserDetailResponse
import com.challenge.github.model.response.UserRepositoryResponse
import com.challenge.github.model.response.UserResponse

interface GitHubRepository {
    suspend fun getUserList(): NetworkResult<List<UserResponse>>

    suspend fun getUser(userLogin: String): NetworkResult<UserDetailResponse>

    suspend fun getUserRepositories(userLogin: String): NetworkResult<List<UserRepositoryResponse>>
}
