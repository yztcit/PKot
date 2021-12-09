package com.nttn.pkot

import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nttn.pkot.base.BaseVBActivity
import com.nttn.pkot.base.BaseViewModel
import com.nttn.pkot.data.model.MainData
import com.nttn.pkot.view.PrimaryActivity
import com.nttn.pkot.view.adapter.MainAdapter
import com.nttn.pkot.view.feature.MaterialDesignActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : BaseVBActivity<MainActivityBinding, BaseViewModel>() {
    private lateinit var mainAdapter: MainAdapter
    private lateinit var itemList: ArrayList<MainData>

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun exitAfterTwice() = true

    override fun initView() {
        setSupportActionBar(mBinding.toolbarLayout.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.mipmap.ic_menu)
        }

        mainAdapter = MainAdapter(fakeItemData())
        mBinding.recyclerview.let {
            it.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            it.adapter = mainAdapter
        }

        val drawerToggle = ActionBarDrawerToggle(
            this,
            mBinding.drawerLayout,
            mBinding.toolbarLayout.toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ).apply {
            syncState()
        }

        mBinding.drawerLayout.addDrawerListener(drawerToggle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    mBinding.drawerLayout.openDrawer(GravityCompat.START)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fakeItemData(): ArrayList<MainData> = ArrayList<MainData>().apply {
        add(MainData(1, getString(R.string.column_primary), PrimaryActivity::class.java.name))
        add(MainData(2, getString(R.string.expand_list), PrimaryActivity::class.java.name))
        add(MainData(3, getString(R.string.material_design_style), MaterialDesignActivity::class.java.name))

        itemList = this
    }
}