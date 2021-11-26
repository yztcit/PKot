package com.nttn.pkot.data.api

import com.nttn.pkot.data.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}