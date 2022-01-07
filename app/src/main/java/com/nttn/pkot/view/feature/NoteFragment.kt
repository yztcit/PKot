package com.nttn.pkot.view.feature

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.nttn.pkot.CuzApplication
import com.nttn.pkot.NoteFragmentBinding
import com.nttn.pkot.R
import com.nttn.pkot.base.BaseVBFragment
import com.nttn.pkot.data.room.Note
import com.nttn.pkot.view.adapter.DataAdapter
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class NoteFragment : BaseVBFragment<NoteFragmentBinding>() {
    private val mAdapter = DataAdapter(arrayListOf())

    override fun getLayoutId() = R.layout.fragment_note

    override fun initView() {
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(mBinding.toolbarLayout.toolbar)
            it.supportActionBar?.run {
                //setDisplayHomeAsUpEnabled(true)
                setHasOptionsMenu(true)
            }
        }

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
            ToastUtils.showShort("todo: 跳转新建笔记页面")
            lifecycleScope.launch(Dispatchers.IO) {
                val note = Note(1, "test", Date(), "test room db")
                CuzApplication.obtainDB().noteDao().insertNote(note)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_note_tool, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.layout).title = "列表视图"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                showSearchNoteView()
                return true
            }
            R.id.layout -> {
                changeLayoutManager()
                return true
            }
            R.id.del_multi -> {
                multiDelNotes()
                return true
            }
            R.id.sort_by -> {
                showSortByView()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showSearchNoteView() {
        ToastUtils.showShort(R.string.note_menu_search)
    }

    private fun changeLayoutManager() {
        ToastUtils.showShort(R.string.note_menu_layout)
    }

    private fun multiDelNotes() {
        ToastUtils.showShort(R.string.note_menu_del_multi)
    }

    private fun showSortByView() {
        ToastUtils.showShort(R.string.note_menu_sort_by)
    }
}