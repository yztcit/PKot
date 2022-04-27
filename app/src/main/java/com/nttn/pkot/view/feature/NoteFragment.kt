package com.nttn.pkot.view.feature

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.nttn.pkot.CuzApplication
import com.nttn.pkot.NoteFragmentBinding
import com.nttn.pkot.R
import com.nttn.baselib.base.BaseVBFragment
import com.nttn.pkot.data.room.Note
import com.nttn.pkot.view.adapter.NoteAdapter
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
class NoteFragment : BaseVBFragment<NoteFragmentBinding>() {
    private val mNotes = MutableLiveData<ArrayList<Note>>(arrayListOf())
    private val mAdapter = NoteAdapter(mNotes.value!!)

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
            findNavController().navigate(NavFragmentDirections.actionToCreateNote())
        }

        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO) {
                mNotes.postValue(CuzApplication.obtainDB().noteDao().getAllNotes() as ArrayList<Note>)
            }
        }

        mNotes.observe(this) { mAdapter.refreshData(it) }
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