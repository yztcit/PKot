package com.nttn.pkot.view.adapter

import com.bumptech.glide.Glide
import com.nttn.pkot.R
import com.nttn.pkot.UserItemBinding
import com.nttn.pkot.base.AbstractVBAdapter
import com.nttn.pkot.data.model.SampleData
import com.nttn.pkot.view.feature.DetailActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class DataAdapter(sampleData: ArrayList<SampleData>) :
    AbstractVBAdapter<SampleData, UserItemBinding>(sampleData, listener = { view, position ->
        DetailActivity.seeDetail(view.context, sampleData[position])
    }, action = { binding, position ->
        val data = sampleData[position]
        binding.run {
            name.text = data.name
            email.text = data.email
            Glide.with(root.context).load(data.avatar).into(image)
        }
    }) {

    override fun getLayoutId() = R.layout.item_user_layout
}