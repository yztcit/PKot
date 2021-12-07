package com.nttn.pkot.data.repository

import com.nttn.pkot.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getUsers(pageNum: Int, pageSize: Int) = apiHelper.getUsers(pageNum, pageSize)
}