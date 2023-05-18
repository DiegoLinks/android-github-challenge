package com.challenge.github.core.network

import com.challenge.github.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface GitHubApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<UserResponse>>
}