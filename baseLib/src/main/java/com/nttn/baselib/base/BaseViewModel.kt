package com.nttn.baselib.base

import androidx.lifecycle.ViewModel
import com.nttn.baselib.intent.MainIntent
import kotlinx.coroutines.channels.Channel

open class BaseViewModel(var showWatermark: Boolean = true) : ViewModel() {
    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
}