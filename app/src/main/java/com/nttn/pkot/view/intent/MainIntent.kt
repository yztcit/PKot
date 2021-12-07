package com.nttn.pkot.view.intent

sealed class MainIntent {
    object Refresh: MainIntent()
    object LoadMore: MainIntent()
    object FetchData: MainIntent()
}
