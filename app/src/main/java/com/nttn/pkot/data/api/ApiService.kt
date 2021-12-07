package com.nttn.pkot.data.api

import com.nttn.pkot.base.BaseResponse
import com.nttn.pkot.base.BaseApiService
import com.nttn.pkot.data.model.SampleData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService : BaseApiService {
    @GET("users/list")
    suspend fun getUsers(
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    ): BaseResponse<List<SampleData>>
}