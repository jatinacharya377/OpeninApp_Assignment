package com.assignment.openinapp.data.api

import com.assignment.openinapp.BuildConfig
import com.assignment.openinapp.utils.PrefsManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT = 30L
private const val READ_TIMEOUT = 30L
private const val WRITE_TIMEOUT = 30L

class ApiBuilder {
    private fun getRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val requestInterceptor= Interceptor {chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .build()
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Token " + PrefsManager.getString(PrefsManager.USER_TOKEN).toString())
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }
        val client: OkHttpClient = OkHttpClient
            .Builder().apply {
                addInterceptor(interceptor)
                addInterceptor(requestInterceptor)
                connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                retryOnConnectionFailure(true)
            }.build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApi(): OpenInAppApi = getRetrofit().create(OpenInAppApi::class.java)
}