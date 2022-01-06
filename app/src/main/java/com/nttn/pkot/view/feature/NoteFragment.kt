package com.nttn.pkot.view.feature

import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import com.nttn.pkot.CuzApplication
import com.nttn.pkot.NoteFragmentBinding
import com.nttn.pkot.R
import com.nttn.pkot.base.BaseVBFragment
import com.nttn.pkot.data.room.Note

class NoteFragment: BaseVBFragment<NoteFragmentBinding>() {

    override fun getLayoutId() = R.layout.fragment_note

    override fun initView() {
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(mBinding.toolbarLayout.toolbar)
            it.supportActionBar?.run {
                //setDisplayHomeAsUpEnabled(true)
                setHasOptionsMenu(true)
            }
        }

        val note1 = Note()

        mBinding.btnAdd.setOnClickListener {
            CuzApplication.sDataBase.noteDao().insertNote(note1)
        }
        mBinding.btnDel.setOnClickListener {
            CuzApplication.sDataBase.noteDao().deleteNote(note1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_navi, menu)
    }
}