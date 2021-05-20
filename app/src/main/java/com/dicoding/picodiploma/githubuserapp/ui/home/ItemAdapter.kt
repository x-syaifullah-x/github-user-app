package com.dicoding.picodiploma.githubuserapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.githubuserapp.data.remote.response.User
import com.dicoding.picodiploma.githubuserapp.databinding.ItemUserBinding

class ItemAdapter(
    private val blockOnItemClick: (User) -> Unit = {},
) : RecyclerView.Adapter<ItemAdapter.UserViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var list = ArrayList<User>()

    fun setList(users: ArrayList<User>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.data = user
            binding.root.setOnClickListener { blockOnItemClick(user) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int) = position.toLong()
}