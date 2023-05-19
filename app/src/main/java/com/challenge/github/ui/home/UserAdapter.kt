package com.challenge.github.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.github.R
import com.challenge.github.model.User

class UserAdapter(
    private val userList: List<User>,
    private val onUserItemClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
        holder.itemView.setOnClickListener { onUserItemClick(user) }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvUrl: TextView = itemView.findViewById(R.id.tv_url)
        fun bind(user: User) {
            Glide
                .with(itemView.context)
                .load(user.avatarUrl)
                .circleCrop()
                .placeholder(R.color.black)
                .into(ivAvatar)
            tvName.text = user.login
            tvUrl.text = user.url
        }
    }
}