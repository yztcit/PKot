package com.nttn.pkot.data.api

import com.nttn.pkot.base.BaseResponse
import com.nttn.pkot.base.BaseService
import com.nttn.pkot.data.model.User
import retrofit2.http.GET

interface ApiService : BaseService {
    @GET("users/list")
    suspend fun getUsers(): BaseResponse<List<User>>
}