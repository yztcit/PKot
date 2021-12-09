package com.nttn.pkot.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractVBAdapter<T, VB : ViewDataBinding>(
    private val data: ArrayList<T>? = null,
    private val listener: (view: View, position: Int) -> Unit = { _, _ -> },
    private val action: (VB, position: Int) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<AbstractVBAdapter<T, VB>.Holder>() {

    inner class Holder(private var binding: VB) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            action(binding, position)
            itemView.setOnClickListener {
                listener(it, position)
            }
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                getLayoutId(),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return if (data.isNullOrEmpty()) 0 else data.size
    }

    fun appendData(list: List<T>) {
        data?.apply {
            val start = size
            addAll(list)
            notifyItemRangeInserted(start, itemCount)
        }
    }

    fun refreshData(list: List<T>) {
        data?.run {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }
    }
}