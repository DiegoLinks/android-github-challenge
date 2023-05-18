package com.challenge.github.core.network

import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val url = "https://api.github.com/"

    private fun getClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS).addInterceptor(interceptor).build()
    }

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(url)
        .client(getClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <API> getService(api: Class<API>): API = retrofitBuilder.create(api)
}
