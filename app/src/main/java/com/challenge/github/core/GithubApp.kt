package com.challenge.github.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GithubApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}