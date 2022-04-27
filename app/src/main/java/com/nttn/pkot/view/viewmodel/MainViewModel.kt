package com.nttn.pkot.view.viewmodel

import androidx.lifecycle.viewModelScope
import com.nttn.baselib.base.BaseViewModel
import com.nttn.pkot.data.repository.MainRepository
import com.nttn.baselib.intent.MainIntent
import com.nttn.pkot.view.viewstate.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : BaseViewModel() {
    private val mainState = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState> get() = mainState

    private var pageNum = 1
    private val pageSize = 10

    init {
        viewModelScope.launch {
            handleIntent()
        }
    }

    private suspend fun handleIntent() {
        userIntent.consumeAsFlow().collect {
            when (it) {
                is MainIntent.FetchData -> {
                    viewModelScope.launch {
                        mainState.value = try {
                            MainState.Loading(repository.getUsers(pageNum, pageSize))
                        } catch (e: Exception) {
                            MainState.Error(e.localizedMessage)
                        }
                    }
                }
                is MainIntent.Refresh -> {
                    pageNum = 1
                    mainState.value = try {
                        MainState.Refreshing(repository.getUsers(pageNum, pageSize))
                    } catch (e: Exception) {
                        MainState.Error(e.localizedMessage)
                    }
                }
                is MainIntent.LoadMore -> {
                    ++pageNum
                    mainState.value = try {
                        MainState.LoadMore(repository.getUsers(pageNum, pageSize))
                    } catch (e: Exception) {
                        if (pageNum > 1) pageNum--
                        MainState.Error(e.localizedMessage)
                    }
                }
            }
        }
    }
}