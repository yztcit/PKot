package com.nttn.baselib.intent

sealed class MainIntent {
    object Refresh: MainIntent()
    object LoadMore: MainIntent()
    object FetchData: MainIntent()
}
