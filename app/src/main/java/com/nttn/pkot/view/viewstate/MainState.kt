package com.nttn.pkot.view.viewstate

import com.nttn.pkot.data.model.User

sealed class MainState {
    object Idle : MainState()
    data class Refreshing(val user: List<User>) : MainState()
    data class LoadMore(val user: List<User>) : MainState()
    data class Loading(val user: List<User>) : MainState()
    data class Error(val error: String?) : MainState()
}
