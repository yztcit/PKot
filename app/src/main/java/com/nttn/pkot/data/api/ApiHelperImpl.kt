package com.nttn.pkot.data.api

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getUsers() = apiService.getUsers()
}