package com.nttn.pkot.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nttn.pkot.R
import com.nttn.pkot.UserItemBinding
import com.nttn.pkot.data.model.SampleData
import com.nttn.pkot.view.DetailActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class DataAdapter(private val sampleData: ArrayList<SampleData>) :
    RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sampleData: SampleData) {
            binding.let { it ->
                it.name.text = sampleData.name
                it.email.text = sampleData.email
                Glide.with(itemView.context).load(sampleData.avatar).into(it.image)
                it.root.setOnClickListener {
                    DetailActivity.seeDetail(it.context, sampleData)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_user_layout, parent, false)
    )


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(sampleData[position])
    }

    override fun getItemCount() = sampleData.size

    fun addData(list: List<SampleData>) {
        val start = sampleData.size
        sampleData.addAll(list)
        notifyItemRangeInserted(start, sampleData.size)
    }

    fun refreshData(list: List<SampleData>) {
        sampleData.run {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }
}