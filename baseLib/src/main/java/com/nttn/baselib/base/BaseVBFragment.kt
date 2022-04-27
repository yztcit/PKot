package com.nttn.baselib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.LogUtils

abstract class BaseVBFragment<VB: ViewDataBinding>: Fragment() {
    protected lateinit var mBinding: VB

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogUtils.d("${this::class.simpleName} onCreateView")
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtils.d("${this::class.simpleName} onViewCreated")
        initView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.d("${this::class.simpleName} onCreate")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("${this::class.simpleName} onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.d("${this::class.simpleName} onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d("${this::class.simpleName} onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtils.d("${this::class.simpleName} onDestroyView")
    }
}