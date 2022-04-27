package com.nttn.pkot.view.feature

import com.nttn.pkot.CategoryFragBinding
import com.nttn.pkot.R
import com.nttn.baselib.base.BaseVBFragment

class CategoryFragment: BaseVBFragment<CategoryFragBinding>() {

    override fun getLayoutId() = R.layout.fragment_category

    override fun initView() {
        val title = arguments?.getString("title", "get null title")
        mBinding.textView.text = title ?: getString(R.string.navi_category)
    }
}