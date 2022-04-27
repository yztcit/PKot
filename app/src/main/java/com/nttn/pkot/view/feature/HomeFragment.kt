package com.nttn.pkot.view.feature

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.nttn.pkot.HomeFragBinding
import com.nttn.pkot.R
import com.nttn.baselib.base.BaseVBFragment
import com.nttn.pkot.data.model.MainData
import com.nttn.baselib.util.alert
import com.nttn.pkot.view.PrimaryActivity
import com.nttn.pkot.view.adapter.MainAdapter
import com.nttn.pkot.view.widget.ExpandListAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class HomeFragment: BaseVBFragment<HomeFragBinding>() {
    private lateinit var mainAdapter: MainAdapter

    override fun getLayoutId() = R.layout.fragment_home

    override fun initView() {
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(mBinding.toolbarLayout.toolbar)
            it.supportActionBar?.run {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.mipmap.ic_menu)
            }
        }

        mainAdapter = MainAdapter(fakeHomeItemData())
        mBinding.recyclerview.run {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = mainAdapter
        }

        val drawerToggle = ActionBarDrawerToggle(
            activity,
            mBinding.drawerLayout,
            mBinding.toolbarLayout.toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ).apply {
            syncState()
        }

        mBinding.drawerLayout.addDrawerListener(drawerToggle)

        mBinding.toolbarLayout.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                android.R.id.home -> if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    mBinding.drawerLayout.openDrawer(GravityCompat.START)
                }
            }
            true
        }

        mBinding.layoutExpand.btnConfirm.setOnClickListener {
            ToastUtils.showShort("clicked confirm")
            mBinding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        mBinding.layoutExpand.btnReset.setOnClickListener {
            ToastUtils.showShort("clicked reset")
            mBinding.drawerLayout.closeDrawer(GravityCompat.START)

            lifecycleScope.launch {
                if (context?.alert("Tips", "Do you confirm?") == true) {
                    //测试navigation 跳转传参
                    findNavController(this@HomeFragment).navigate(
                        NavFragmentDirections.actionToCategoryFragment(
                            getString(R.string.navi_category) + "action"
                        )
                    )
                }
            }
        }

        mBinding.layoutExpand.recyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = ExpandListAdapter(fakeDrawerItemData())
        }
    }

    private fun fakeDrawerItemData(): ArrayList<MainData> = ArrayList<MainData>().apply {
        add(MainData(1, 0, getString(R.string.column_primary), "", "", "", false, ArrayList<MainData>().apply {
            add(MainData(1, 1, getString(R.string.column_primary), getString(R.string.column_primary), "", PrimaryActivity::class.java.name,false, null))
        }))
        add(MainData(2, 0, getString(R.string.column_ui), "","", "", false, ArrayList<MainData>().apply {
            add(MainData(2, 1, getString(R.string.column_ui), getString(R.string.expand_list),"", "", false, null))
            add(MainData(2, 2, getString(R.string.column_ui), getString(R.string.md_sample),"", MaterialDesignActivity::class.java.name,false, null))

            add(MainData(2, 2, getString(R.string.column_ui), "再来一个测试瀑布流布局长文本样式","", "", false, null))
            add(MainData(2, 2, getString(R.string.column_ui), getString(R.string.md_sample),"", "", false, null))
            add(MainData(2, 2, getString(R.string.column_ui), "测试瀑布流布局长文本样式","", "", false, null))
            add(MainData(2, 2, getString(R.string.column_ui), getString(R.string.md_sample),"", "", false, null))
        }))
        add(MainData(3, 0, getString(R.string.column_network), "","", "", false, null))
        add(MainData(4, 0, getString(R.string.column_storage), "","", "", false, null))
        add(MainData(5, 0, getString(R.string.column_third_lib), "","", "", false, null))
        add(MainData(6, 0, getString(R.string.column_optimization), "","", "", false, null))
    }

    private fun fakeHomeItemData(): ArrayList<MainData> = ArrayList<MainData>().apply {
        add(MainData(1, 1, getString(R.string.column_primary), getString(R.string.column_primary), "Kotlin基础语法——变量，方法，循环，面向对象，标准函数、高阶函数，协程等", PrimaryActivity::class.java.name, false, null))
        add(MainData(2, 1, getString(R.string.column_ui), getString(R.string.expand_list), "基于RecyclerView实现的可折叠、展开，同时多级级联的多选列表", "", false, null))
        add(MainData(2, 2, getString(R.string.column_ui), getString(R.string.material_design_style), "Material Design 风格的界面——列表页Toolbar随着列表滑动收起或展开，详情页CollapseToolbar图文", MaterialDesignActivity::class.java.name, false, null))
    }
}