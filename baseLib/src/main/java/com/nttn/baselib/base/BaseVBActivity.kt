package com.nttn.baselib.base

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.nttn.baselib.BaseMarkBinding
import com.nttn.baselib.GlobalHelper
import com.nttn.baselib.R
import java.lang.reflect.ParameterizedType
import kotlin.system.exitProcess

abstract class BaseVBActivity<VB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {
    lateinit var mBinding: VB
    lateinit var mViewModel: VM

    private var exitTime: Long = 0
    private lateinit var baseBinding: BaseMarkBinding

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initView()

    open fun configProviderFactory(): ViewModelProvider.Factory? = null

    protected open fun exitAfterTwice(): Boolean = false

    private fun configViewModel() {
        // Actually ParameterizedType will give us actual type parameters
        val parameterizedType = javaClass.genericSuperclass as? ParameterizedType

        // now get first actual class, which is the class of VM (ProfileVM in this case)
        @Suppress("UNCHECKED_CAST")
        val vmClass = parameterizedType?.actualTypeArguments?.getOrNull(1) as? Class<VM>?

        if (vmClass != null) {
            mViewModel = ViewModelProviders.of(this, configProviderFactory()).get(vmClass)
        } else {
            LogUtils.e("cannot find VM class for $this")
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configViewModel()

        baseBinding = DataBindingUtil.setContentView(this, R.layout.layout_base_watermark)
        baseBinding.contentLayout.let {
            mBinding = DataBindingUtil.inflate(LayoutInflater.from(this), getLayoutId(), it, true)

            it.removeAllViews()
            it.addView(mBinding.root)
        }

        initView()

        //showWatermark
        if (mViewModel.showWatermark) {
            GlobalHelper.watermark.observe(this) { baseBinding.watermark.setImageBitmap(it) }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (!exitAfterTwice()) finish() else dealBackPressedTwice()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (!exitAfterTwice()) return super.onKeyDown(keyCode, event)
        if (KeyEvent.ACTION_DOWN == event.action && KeyEvent.KEYCODE_BACK == keyCode) {
            return dealBackPressedTwice()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        GlobalHelper.watermark.removeObservers(this)
    }

    protected open fun dealBackPressedTwice(): Boolean {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtils.showShort("再按一次退出程序")
            exitTime = System.currentTimeMillis()
        } else {
            GlobalHelper.recycleWatermark()
            finish()
            exitProcess(0)
        }
        return true
    }

    fun displayEmptyView(showEmptyView: Boolean) {
        with(baseBinding.layoutEmpty) {
            when (isInflated) {
                true -> root.visibility = if (showEmptyView) View.VISIBLE else View.GONE
                else -> viewStub?.visibility = if (showEmptyView) View.VISIBLE else View.GONE
            }
        }
    }
}