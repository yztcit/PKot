package com.nttn.pkot

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.nttn.baselib.base.BaseVBActivity
import com.nttn.baselib.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : BaseVBActivity<MainActivityBinding, BaseViewModel>() {
    private lateinit var navController: NavController

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun exitAfterTwice() = true

    override fun initView() {
        navController = Navigation.findNavController(this, R.id.fragment_container)
        NavigationUI.setupWithNavController(mBinding.navigationView, navController)
    }
}