package com.nttn.pkot

import com.nttn.pkot.base.BaseVBActivity
import com.nttn.pkot.view.PrimaryActivity

class MainActivity : BaseVBActivity<MainActivityBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        mBinding.btnPrimary.setOnClickListener { PrimaryActivity.actionStart(this) }
    }
}