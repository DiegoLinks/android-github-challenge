package com.challenge.github.core.repository

import com.challenge.github.core.NetworkResult
import com.challenge.github.model.UserResponse

interface GitHubRepository {
    suspend fun getUserList(): NetworkResult<List<UserResponse>>
}
