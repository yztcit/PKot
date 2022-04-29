package com.nttn.pkot.data.api

import com.nttn.pkot.data.model.SampleData
import com.nttn.pkot.view.feature.handwrite.HandwriteResponse
import okhttp3.RequestBody

interface ApiHelper {
    suspend fun getUsers(pageNum: Int, pageSize: Int): List<SampleData>

    suspend fun handwrite(headers: Map<String, String>, request: RequestBody): HandwriteResponse
}