package com.challenge.github.core.repository

import com.challenge.github.core.network.GitHubApiService
import com.challenge.github.model.User
import com.challenge.github.model.toUser
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val gitHubApiService: GitHubApiService
) : GitHubRepository {

    override suspend fun getUserList(): List<User> {
        return gitHubApiService.getUsers().map { userResponse ->
            userResponse.toUser()
        }
    }
}