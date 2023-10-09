package com.david.githubmobile.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.david.githubmobile.data.local.entity.UserEntity
import com.david.githubmobile.databinding.ItemListUserBinding
import com.david.githubmobile.ui.view.detail.DetailActivity
import com.david.githubmobile.utils.Constant.EXTRA_USER

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val listUser = ArrayList<UserEntity>()

    inner class UserViewHolder(private val view: ItemListUserBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(user: UserEntity) {
            view.apply {
                tvUsername.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivAvatar)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(EXTRA_USER, user.username)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    @SuppressLint("NotifyDataSetChanged")
    fun setAllData(data: List<UserEntity>) {
        listUser.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }
}