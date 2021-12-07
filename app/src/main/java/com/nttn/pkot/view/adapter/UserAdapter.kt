package com.nttn.pkot.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nttn.pkot.R
import com.nttn.pkot.UserItemBinding
import com.nttn.pkot.data.model.User

class UserAdapter(private val users: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.let {
                it.name.text = user.name
                it.email.text = user.email
                Glide.with(itemView.context).load(user.avatar).into(it.image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_user_layout, parent, false)
    )


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size

    fun addData(list: List<User>) {
        val start = users.size
        users.addAll(list)
        notifyItemRangeInserted(start, users.size)
    }

    fun refreshData(list: List<User>) {
        users.run {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }
}