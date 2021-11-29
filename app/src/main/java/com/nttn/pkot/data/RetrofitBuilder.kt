package com.nttn.pkot.data

import com.nttn.pkot.HttpLoggingInterceptor
import com.nttn.pkot.base.BaseService
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

    private fun getOkHttpClient() = OkHttpClient.Builder().let {
        it.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
        it.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
        it.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)

        it.addInterceptor(HttpLoggingInterceptor("PKot").apply {
            setPrintLevel(HttpLoggingInterceptor.Level.BODY)
            setColorLevel(Level.INFO)
        })
    }.build()


    fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getOkHttpClient())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    inline fun <reified T : BaseService> createService(): T = getRetrofit().create(T::class.java)
}