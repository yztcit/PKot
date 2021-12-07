package com.nttn.pkot.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nttn.pkot.data.api.ApiHelper
import com.nttn.pkot.data.repository.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.IllegalArgumentException

@ExperimentalCoroutinesApi
class ViewModelFactory(private val apiHelper: ApiHelper):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}