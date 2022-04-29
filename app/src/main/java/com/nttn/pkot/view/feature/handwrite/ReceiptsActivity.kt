package com.nttn.pkot.view.feature.handwrite

import android.graphics.Bitmap
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.cloudapi.sdk.constant.SdkConstant
import com.alibaba.cloudapi.sdk.signature.SignerFactoryManager
import com.alibaba.cloudapi.sdk.util.SignUtil
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.nttn.baselib.base.BaseVBActivity
import com.nttn.baselib.base.BaseViewModel
import com.nttn.baselib.network.RetrofitBuilder
import com.nttn.pkot.CuzApplication
import com.nttn.pkot.R
import com.nttn.pkot.ReceiptsActivityBinding
import com.nttn.pkot.data.api.ApiHelperImpl
import com.nttn.pkot.data.repository.MainRepository
import com.nttn.pkot.view.feature.NavFragmentDirections
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReceiptsActivity : BaseVBActivity<ReceiptsActivityBinding, BaseViewModel>() {
    private val mReceipts = MutableLiveData<ArrayList<PrismWordsInfo>>(arrayListOf())
    private lateinit var mAdapter: ReceiptAdapter

    override fun getLayoutId(): Int = R.layout.activity_receipts

    override fun initView() {
        SignerFactoryManager.init()

        setSupportActionBar(mBinding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mAdapter = ReceiptAdapter(mReceipts.value!!)

        mBinding.refreshLayout.run {
            setEnableLoadMoreWhenContentNotFull(false)
            setRefreshHeader(MaterialHeader(context))
            setRefreshFooter(ClassicsFooter(context))
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
                override fun onRefresh(refreshLayout: RefreshLayout) {
                    finishRefresh(1500)
                }

                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    finishLoadMore(1500)
                }
            })
        }

        mBinding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mBinding.floating.setOnClickListener {
            ToastUtils.showShort("扫描单据")
            //findNavController(it).navigate(NavFragmentDirections.actionToCreateNote())
        }

        mReceipts.observe(this) {
            mAdapter.refreshData(it)
        }

        scanReceipt()
    }

    private fun scanReceipt() {
        lifecycleScope.launch {
            val drawable2Bytes = ImageUtils.drawable2Bytes(
                resources.getDrawable(R.drawable.doc),
                Bitmap.CompressFormat.JPEG,
                100
            )

            val toByteArray = GsonUtils.toJson(
                HandwriteReq(Base64.encodeToString(drawable2Bytes, Base64.NO_WRAP))
            ).toByteArray(SdkConstant.CLOUDAPI_ENCODING)

            mReceipts.postValue(
                // TODO: 扫描单据
                MainRepository(ApiHelperImpl(RetrofitBuilder.createService()))
                    .handwrite(toByteArray).prism_wordsInfo as ArrayList<PrismWordsInfo>?
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_receipts, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_receipt -> {
                ToastUtils.showShort("搜索单据")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}