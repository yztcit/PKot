package com.nttn.pkot.view.viewstate

import com.nttn.pkot.data.model.SampleData

sealed class MainState {
    object Idle : MainState()
    data class Refreshing(val sampleData: List<SampleData>) : MainState()
    data class LoadMore(val sampleData: List<SampleData>) : MainState()
    data class Loading(val sampleData: List<SampleData>) : MainState()
    data class Error(val error: String?) : MainState()
}
