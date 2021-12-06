package com.nttn.pkot.data

import com.nttn.pkot.base.BaseApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Level

/**
 * 接口通过Rap2 mock
 * @see <a href="http://rap2.taobao.org/repository/editor?id=294816&itf=2147814">Rap2 mock api</a>
 */
object RetrofitBuilder {
    private const val BASE_URL = "http://rap2api.taobao.org/app/mock/294816/"
    private const val DEFAULT_MILLISECONDS = 60000L

    val rtf: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder().let {
            it.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
            it.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
            it.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)

            it.addInterceptor(HttpLoggingInterceptor("PKot").apply {
                setPrintLevel(HttpLoggingInterceptor.Level.BODY)
                setColorLevel(Level.INFO)
            })
        }.build()
    }

    inline fun <reified T : BaseApiService> createService(): T = rtf.create(T::class.java)
}