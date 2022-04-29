package com.nttn.pkot.data.api

import com.nttn.baselib.base.BaseApiService
import com.nttn.baselib.base.BaseResponse
import com.nttn.pkot.data.model.SampleData
import com.nttn.pkot.view.feature.handwrite.HandwriteConst
import com.nttn.pkot.view.feature.handwrite.HandwriteResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService : BaseApiService {
    @GET("users/list")
    suspend fun getUsers(
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    ): BaseResponse<List<SampleData>>

    @POST("https://" + HandwriteConst.HOST + HandwriteConst.pathShouxieCn)
    suspend fun handwrite(
        @HeaderMap headers: Map<String, @JvmSuppressWildcards String>,
        @Body requestBody: RequestBody
    ): HandwriteResponse
}