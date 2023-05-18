package com.challenge.github.core.repository

import com.challenge.github.core.NetworkResult
import com.challenge.github.model.UserDetailResponse
import com.challenge.github.model.UserRepositoryResponse
import com.challenge.github.model.UserResponse

interface GitHubRepository {
    suspend fun getUserList(): NetworkResult<List<UserResponse>>

    suspend fun getUser(userLogin: String): NetworkResult<UserDetailResponse>

    suspend fun getUserRepositories(userLogin: String): NetworkResult<List<UserRepositoryResponse>>
}
