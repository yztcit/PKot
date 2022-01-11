package com.nttn.pkot.base

import androidx.lifecycle.ViewModel
import com.nttn.pkot.view.intent.MainIntent
import kotlinx.coroutines.channels.Channel

open class BaseViewModel(var showWatermark: Boolean = true) : ViewModel() {
    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
}