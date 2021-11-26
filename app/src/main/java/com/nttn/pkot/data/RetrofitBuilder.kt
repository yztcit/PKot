package com.nttn.pkot.data

import com.nttn.pkot.HttpLoggingInterceptor
import com.nttn.pkot.data.api.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Level

object RetrofitBuilder {
    private const val BASE_URL = "https://test.mockapi.io/"
    private const val DEFAULT_MILLISECONDS = 60000L

    private fun getOkHttpClient() = OkHttpClient.Builder().let {
        it.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
        it.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
        it.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)

        it.addInterceptor(HttpLoggingInterceptor("PKot").apply {
            setPrintLevel(HttpLoggingInterceptor.Level.BODY)
            setColorLevel(Level.INFO)
        })
    }.build()


    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getOkHttpClient())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

}