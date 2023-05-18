package com.challenge.github.core.network

import com.challenge.github.model.UserDetailResponse
import com.challenge.github.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<UserResponse>>

    @GET("users/{user}")
    suspend fun getUser(@Path("user") user: String): Response<UserDetailResponse>
}