package com.nttn.pkot.data.repository

import com.nttn.pkot.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getUsers() = apiHelper.getUsers()
}