package com.nttn.pkot.data.api

import com.nttn.pkot.data.model.SampleData

interface ApiHelper {
    suspend fun getUsers(pageNum: Int, pageSize: Int): List<SampleData>
}