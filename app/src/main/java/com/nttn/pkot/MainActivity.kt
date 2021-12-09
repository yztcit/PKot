package com.nttn.pkot

import com.nttn.pkot.base.BaseVBActivity
import com.nttn.pkot.base.BaseViewModel
import com.nttn.pkot.view.PrimaryActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : BaseVBActivity<MainActivityBinding, BaseViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        setSupportActionBar(mBinding.toolbarLayout.toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mBinding.btnPrimary.setOnClickListener { PrimaryActivity.actionStart(this) }
    }

    override fun exitAfterTwice() = true
}