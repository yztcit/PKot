package com.nttn.pkot

import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nttn.pkot.base.BaseVBActivity
import com.nttn.pkot.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class EntryActivity: BaseVBActivity<EntryActivityBinding, BaseViewModel>() {
    private lateinit var navController: NavController

    override fun getLayoutId() = R.layout.activity_entry

    override fun initView() {
        navController = Navigation.findNavController(this, R.id.navHost)
    }
}