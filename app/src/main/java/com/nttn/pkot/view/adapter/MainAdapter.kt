package com.nttn.pkot.view.adapter

import android.content.Intent
import android.view.View
import com.nttn.pkot.R
import com.nttn.pkot.base.AbstractVBAdapter
import com.nttn.pkot.data.model.MainData
import com.nttn.pkot.databinding.MainItemBinding

class MainAdapter(
    list: ArrayList<MainData>,
    private var listener: (view: View, data: MainData) -> Unit = { view, data ->
        if (data.navigation.isNotEmpty()) {
            view.context.startActivity(Intent(view.context, Class.forName(data.navigation)))
        }
    }
) :
    AbstractVBAdapter<MainData, MainItemBinding>(list, listener = { itemView, position ->
        listener(itemView, list[position])
    }, onBind = { binding, position ->
        val mainData = list[position]
        binding.tvDescription.text = mainData.description
        binding.tvName.text = mainData.roomName
        binding.tvName.setOnClickListener {
            if (mainData.navigation.isNotEmpty()) {
                it.context.startActivity(Intent(it.context, Class.forName(mainData.navigation)))
            }
        }
    }) {

    override fun getLayoutId() = R.layout.item_main_layout
}