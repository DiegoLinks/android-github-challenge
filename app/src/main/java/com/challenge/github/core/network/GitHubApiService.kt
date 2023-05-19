package com.challenge.github.core.network

import com.challenge.github.model.response.UserDetailResponse
import com.challenge.github.model.response.UserRepositoryResponse
import com.challenge.github.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<UserResponse>>

    @GET("users/{user}")
    suspend fun getUser(@Path("user") user: String): Response<UserDetailResponse>

    @GET("users/{user}/repos")
    suspend fun getUserRepositories(@Path("user") user: String): Response<List<UserRepositoryResponse>>
}