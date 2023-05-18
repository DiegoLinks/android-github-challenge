package com.challenge.github.core.di

import com.challenge.github.core.network.GitHubApiService
import com.challenge.github.core.network.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Provides
    fun providesGithubApi(
        serviceProvider: RetrofitClient
    ) = serviceProvider.getService(GitHubApiService::class.java)
}