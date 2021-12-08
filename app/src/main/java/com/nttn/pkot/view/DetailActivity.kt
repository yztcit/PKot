package com.nttn.pkot.view

import android.content.Context
import android.content.Intent
import android.view.MenuItem
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
        val sampleData: SampleData? = intent.getParcelableExtra("data")
        setSupportActionBar(mBinding.collapseToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sampleData?.let {
            mBinding.collapseToolbar.title = it.name
            Glide.with(this).load(it.avatar).into(mBinding.collapseToolbar.image)
            mBinding.content.text = it.content?.repeat(10)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}