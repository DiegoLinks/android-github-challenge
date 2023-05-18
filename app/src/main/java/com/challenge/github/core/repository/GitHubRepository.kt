package com.challenge.github.core.repository

import com.challenge.github.model.User

interface GitHubRepository {
    suspend fun getUserList(): List<User>
}
