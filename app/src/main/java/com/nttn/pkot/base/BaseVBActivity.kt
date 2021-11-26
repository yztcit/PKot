package com.nttn.pkot.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseVBActivity<VB : ViewDataBinding> : AppCompatActivity() {
    lateinit var mBinding: VB

    @LayoutRes abstract fun getLayoutId() : Int

    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, getLayoutId())

        initView()
    }
}