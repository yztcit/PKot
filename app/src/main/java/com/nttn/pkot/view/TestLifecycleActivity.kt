package com.nttn.pkot.view

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.nttn.pkot.R
import com.nttn.pkot.TestLifecycleBinding
import com.nttn.pkot.base.BaseVBActivity
import com.nttn.pkot.data.RetrofitBuilder
import com.nttn.pkot.data.api.ApiHelperImpl
import com.nttn.pkot.view.adapter.UserAdapter
import com.nttn.pkot.view.intent.MainIntent
import com.nttn.pkot.view.viewmodel.MainViewModel
import com.nttn.pkot.view.viewmodel.ViewModelFactory
import com.nttn.pkot.view.viewstate.MainState
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class TestLifecycleActivity : BaseVBActivity<TestLifecycleBinding, MainViewModel>() {
    private val mAdapter = UserAdapter(arrayListOf())

    override fun getLayoutId() = R.layout.activity_test_lifecycle

    override fun initView() {
        mBinding.refreshLayout.run {
            setEnableLoadMoreWhenContentNotFull(true)
            setRefreshHeader(MaterialHeader(this@TestLifecycleActivity))
            setRefreshFooter(ClassicsFooter(this@TestLifecycleActivity))
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
            layoutManager = LinearLayoutManager(this@TestLifecycleActivity)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = mAdapter
        }

        lifecycleScope.launch {
            mViewModel.run {
                userIntent.send(MainIntent.FetchUser)
                state.collect {
                    when (it) {
                        is MainState.Idle -> {

                        }
                        is MainState.Loading -> {
                            //loading dialog
                            mAdapter.refreshData(it.user)
                            displayEmptyView(mAdapter.itemCount <= 0)
                        }
                        is MainState.Refreshing -> {
                            mBinding.refreshLayout.finishRefresh()
                            mAdapter.refreshData(it.user)
                            displayEmptyView(mAdapter.itemCount <= 0)
                        }
                        is MainState.LoadMore -> {
                            mBinding.refreshLayout.finishLoadMore()
                            mAdapter.addData(it.user)
                            displayEmptyView(mAdapter.itemCount <= 0)
                        }
                        is MainState.Error -> {
                            displayEmptyView(mAdapter.itemCount <= 0)
                            ToastUtils.showShort(it.error)
                        }
                    }
                }
            }
        }
    }

    override fun configProviderFactory() = ViewModelFactory(ApiHelperImpl(RetrofitBuilder.createService()))
}