package com.nttn.pkot.view.adapter

import com.bumptech.glide.Glide
import com.nttn.pkot.R
import com.nttn.pkot.UserItemBinding
import com.nttn.pkot.base.AbstractVBAdapter
import com.nttn.pkot.data.model.SampleData
import com.nttn.pkot.view.DetailActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class DataAdapter(sampleData: ArrayList<SampleData>) :
    AbstractVBAdapter<SampleData, UserItemBinding>(sampleData, { position ->
        val data = sampleData[position]
        let {
            it.name.text = data.name
            it.email.text = data.email
            Glide.with(root.context).load(data.avatar).into(it.image)
            it.root.setOnClickListener {
                DetailActivity.seeDetail(root.context, data)
            }
        }
    }) {

    override fun getLayoutId() = R.layout.item_user_layout
}