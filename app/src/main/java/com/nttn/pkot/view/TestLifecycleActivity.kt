package com.nttn.pkot.view

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.nttn.pkot.R
import com.nttn.pkot.TestLifecycleBinding
import com.nttn.pkot.base.BaseVBActivity
import com.nttn.pkot.data.RetrofitBuilder
import com.nttn.pkot.data.api.ApiHelperImpl
import com.nttn.pkot.data.model.User
import com.nttn.pkot.view.adapter.UserAdapter
import com.nttn.pkot.view.intent.MainIntent
import com.nttn.pkot.view.viewmodel.MainViewModel
import com.nttn.pkot.view.viewmodel.ViewModelFactory
import com.nttn.pkot.view.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TestLifecycleActivity : BaseVBActivity<TestLifecycleBinding>() {
    @ExperimentalCoroutinesApi
    private lateinit var mainViewModel: MainViewModel
    private val mAdapter = UserAdapter(arrayListOf())

    override fun getLayoutId() = R.layout.activity_test_lifecycle

    @ExperimentalCoroutinesApi
    override fun initView() {
        mBinding.recyclerview.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerview.apply {
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = mAdapter
        }

        mainViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(MainViewModel::class.java)

        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {
                    }
                    is MainState.Loading -> {
                        mBinding.btnFetchUser.visibility = View.GONE
                    }
                    is MainState.Users -> {
                        mBinding.btnFetchUser.visibility = View.GONE
                        renderList(it.user)
                    }
                    is MainState.Error -> {
                        mBinding.btnFetchUser.visibility = View.VISIBLE
                        ToastUtils.showShort(it.error)
                    }
                }
            }
        }

        mBinding.text.setOnClickListener {
            startActivity(Intent(this@TestLifecycleActivity, PrimaryActivity::class.java))
        }

        mBinding.btnFetchUser.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchUser)
            }
        }
    }

    private fun renderList(users: List<User>) {
        users.let {
            mAdapter.addData(it)
            mAdapter.notifyDataSetChanged()
        }
    }
}