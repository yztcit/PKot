package com.nttn.pkot.view.feature

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.nttn.pkot.R
import com.nttn.pkot.TestLifecycleBinding
import com.nttn.pkot.base.BaseVBActivity
import com.nttn.pkot.data.RetrofitBuilder
import com.nttn.pkot.data.api.ApiHelperImpl
import com.nttn.pkot.view.adapter.DataAdapter
import com.nttn.pkot.view.intent.MainIntent
import com.nttn.pkot.view.viewmodel.MainViewModel
import com.nttn.pkot.view.viewmodel.ViewModelFactory
import com.nttn.pkot.view.viewstate.MainState
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MaterialDesignActivity : BaseVBActivity<TestLifecycleBinding, MainViewModel>() {
    private val mAdapter = DataAdapter(arrayListOf())

    override fun getLayoutId() = R.layout.activity_test_lifecycle

    override fun configProviderFactory() = ViewModelFactory(ApiHelperImpl(RetrofitBuilder.createService()))

    override fun initView() {
        setSupportActionBar(mBinding.titleLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mBinding.refreshLayout.run {
            setEnableLoadMoreWhenContentNotFull(false)
            setRefreshHeader(MaterialHeader(this@MaterialDesignActivity))
            setRefreshFooter(ClassicsFooter(this@MaterialDesignActivity))
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
                override fun onRefresh(refreshLayout: RefreshLayout) {
                    lifecycleScope.launch { mViewModel.userIntent.send(MainIntent.Refresh) }
                }

                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    lifecycleScope.launch { mViewModel.userIntent.send(MainIntent.LoadMore) }
                }
            })
        }

        mBinding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MaterialDesignActivity)
            adapter = mAdapter
        }

        lifecycleScope.launch {
            mViewModel.run {
                state.collect {
                    when (it) {
                        is MainState.Idle -> {
                            userIntent.send(MainIntent.FetchData)
                        }
                        is MainState.Loading -> {
                            //loading dialog
                            mAdapter.refreshData(it.sampleData)
                        }
                        is MainState.Refreshing -> {
                            mBinding.refreshLayout.finishRefresh()
                            mAdapter.refreshData(it.sampleData)
                        }
                        is MainState.LoadMore -> {
                            mBinding.refreshLayout.finishLoadMore()
                            mAdapter.appendData(it.sampleData)
                        }
                        is MainState.Error -> {
                            ToastUtils.showShort(it.error)
                        }
                    }
                    displayEmptyView(mAdapter.itemCount <= 0)
                }
            }
        }
    }
}