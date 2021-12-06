package com.nttn.pkot.base

import androidx.lifecycle.ViewModel
import com.nttn.pkot.view.intent.MainIntent
import com.nttn.pkot.view.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
open class BaseViewModel(var showWatermark: Boolean = true) : ViewModel() {
    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    protected val mainState = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState> get() = mainState
}