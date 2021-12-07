package com.nttn.pkot.data.api

import com.blankj.utilcode.util.ToastUtils
import com.nttn.pkot.data.model.SampleData

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getUsers(pageNum: Int, pageSize: Int): List<SampleData> =
        apiService.getUsers(pageNum, pageSize).run {
            when (code) {
                200 -> data
                else -> {
                    ToastUtils.showShort(msg)
                    return listOf()
                }
            }
        }
}