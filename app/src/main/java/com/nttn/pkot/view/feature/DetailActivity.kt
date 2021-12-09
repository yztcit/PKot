package com.nttn.pkot.view.feature

import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.nttn.pkot.DetailActBinding
import com.nttn.pkot.R
import com.nttn.pkot.base.BaseVBActivity
import com.nttn.pkot.base.BaseViewModel
import com.nttn.pkot.data.model.SampleData
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class DetailActivity : BaseVBActivity<DetailActBinding, BaseViewModel>() {
    companion object {
        fun seeDetail(context: Context, sampleData: SampleData) {
            context.startActivity(Intent(context, DetailActivity::class.java).apply {
                putExtra("data", sampleData)
            })
        }
    }

    override fun getLayoutId() = R.layout.activity_detail

    override fun initView() {
        setSupportActionBar(mBinding.collapseToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sampleData: SampleData? = intent.getParcelableExtra("data")
        sampleData?.let {
            mBinding.collapseToolbar.title = it.name
            Glide.with(this).load(it.avatar).into(mBinding.collapseToolbar.image)
            mBinding.content.text = it.content?.repeat(10)
        }
    }
}