package com.nttn.pkot.view.feature.note

import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.nttn.pkot.CuzApplication
import com.nttn.pkot.NoteCreateBinding
import com.nttn.pkot.R
import com.nttn.baselib.base.BaseVBActivity
import com.nttn.baselib.base.BaseViewModel
import com.nttn.pkot.data.room.Note
import kotlinx.coroutines.*
import java.util.*

@ExperimentalCoroutinesApi
class NoteCreateActivity : BaseVBActivity<NoteCreateBinding, BaseViewModel>() {
    private var mCurDate: Date? = null
    private var noteId: Int = -1
    private var note = Note(null, null, null, null)

    override fun getLayoutId() = R.layout.activity_note_create

    override fun initView() {
        setSupportActionBar(mBinding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        noteId = intent.extras!!.getInt("noteId", -1)

        lifecycleScope.launchWhenCreated {
            // 点击列表item，传递noteId
            if (noteId > 0) {
                note = withContext(Dispatchers.IO) {
                    CuzApplication.obtainDB().noteDao().queryNoteById(noteId)
                }
            }

            note.let {
                mBinding.tvTitle.setText(it.title)

                mCurDate = it.date ?: TimeUtils.getNowDate()
                mBinding.tvDateTime.text = TimeUtils.getFriendlyTimeSpanByNow(mCurDate)

                mBinding.btnNoteType.text = it.type ?: "开发"

                mBinding.etNote.setText(it.content)
                mBinding.etNote.setSelection(it.content?.length ?: 0)
                mBinding.etNote.requestFocus()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note_create, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.tick -> {
                lifecycleScope.launch {
                    val insertResult: Long = withContext(Dispatchers.IO) {
                        note.let {
                            it.title = mBinding.tvTitle.text.toString()
                            it.date = mCurDate
                            it.type = mBinding.btnNoteType.text.toString()
                            it.content = mBinding.etNote.text.toString()
                        }

                        CuzApplication.obtainDB().noteDao().insertNote(note)
                    }

                    LogUtils.d("insert note > $insertResult")

                    when (insertResult > 0) {
                        true -> {
                            ToastUtils.showShort("保存成功~")
                            finish()
                        }
                        else -> ToastUtils.showShort("保存失败！")
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}