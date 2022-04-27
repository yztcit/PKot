package com.nttn.pkot.view.widget

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.nttn.pkot.ExpandFloorBinding
import com.nttn.pkot.ExpandRoomBinding
import com.nttn.pkot.R
import com.nttn.baselib.base.AbstractVBAdapter
import com.nttn.pkot.data.model.MainData

class ExpandListAdapter(
    expandList: ArrayList<MainData>,
    listener: (floor: Int, room: Int) -> Unit = { _, _ -> }
) :
    AbstractVBAdapter<MainData, ExpandFloorBinding>(
        expandList,
        onBind = { binding, position ->
            val data = expandList[position]

            val roomAdapter = RoomAdapter(arrayListOf<MainData>().apply {
                addAll(if (data.checked) data.children ?: arrayListOf() else arrayListOf())
            }) {
                ToastUtils.showShort("clicked floor$position, room$it")
                listener(position, it)
            }

            binding.tvColumn.text = data.floorName

            binding.btnExpand.run {
                visibility = if (data.children.isNullOrEmpty()) View.GONE else View.VISIBLE

                text =
                    if (data.checked) binding.root.context.getString(R.string.collapse) else binding.root.context.getString(
                        R.string.expand
                    )

                setOnClickListener {
                    //expand or collapse
                    if (data.checked) roomAdapter.clearAll()
                    else roomAdapter.refreshData(data.children ?: arrayListOf())

                    data.checked = !data.checked

                    text =
                        if (data.checked) binding.root.context.getString(R.string.collapse) else binding.root.context.getString(
                            R.string.expand
                        )
                }
            }

            binding.rcyFloor.run {
                if (layoutManager == null) layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = roomAdapter
            }
        }) {


    override fun getLayoutId() = R.layout.item_expand_floor
}

class RoomAdapter(expandList: ArrayList<MainData>, listener: (position: Int) -> Unit = {}) :
    AbstractVBAdapter<MainData, ExpandRoomBinding>(
        expandList,
        listener = { view, position ->
            if (expandList[position].navigation.isNotEmpty()) {
                view.context.startActivity(Intent(view.context, Class.forName(expandList[position].navigation)))
            }
            listener(position)
        },
        onBind = { binding, position ->
            val data = expandList[position]
            binding.name.text = data.roomName
        }) {


    override fun getLayoutId() = R.layout.item_expand_room
}