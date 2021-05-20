package com.consumer.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.consumer.app.databinding.ItemBinding

class ItemAdapter(
    private val blockOnButtonDeleteClick: (Int) -> Unit = {},
) : RecyclerView.Adapter<ItemAdapter.UserViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var list = ArrayList<Model>()

    fun setList(users: ArrayList<Model>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: Model) {
            binding.apply {
                data = user
                Glide.with(ivUser).load(user.avatar_url).centerCrop().into(ivUser)
                binding.btnDelete.setOnClickListener { blockOnButtonDeleteClick(user.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int) = position.toLong()
}