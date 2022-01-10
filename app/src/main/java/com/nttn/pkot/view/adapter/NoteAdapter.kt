package com.nttn.pkot.view.adapter

import androidx.navigation.Navigation
import com.blankj.utilcode.util.TimeUtils
import com.nttn.pkot.NoteItemBinding
import com.nttn.pkot.R
import com.nttn.pkot.base.AbstractVBAdapter
import com.nttn.pkot.data.room.Note
import com.nttn.pkot.view.feature.NavFragmentDirections

class NoteAdapter(notes: ArrayList<Note>) :
    AbstractVBAdapter<Note, NoteItemBinding>(notes, listener = { view, position ->
        Navigation.findNavController(view).navigate(NavFragmentDirections.actionToCreateNote(notes[position].id))
    }, onBind = { binding, position ->
        binding.tvContent.text = notes[position].content
        binding.tvDateTime.text = TimeUtils.getFriendlyTimeSpanByNow(notes[position].date)
    }) {
    override fun getLayoutId() = R.layout.item_note_layout
}