package com.challenge.github.core.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody

class ErrorResponseConvert {
    companion object {
         private fun createMoshi(): Moshi {
            return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

         fun convertErrorBody(errorBody: ResponseBody?): ErrorResponse? {
            return try {
                errorBody?.source()?.let {
                    val moshiAdapter = createMoshi().adapter(ErrorResponse::class.java)
                    moshiAdapter.fromJson(it)
                }
            } catch (exception: Exception) {
                null
            }
        }
    }
}