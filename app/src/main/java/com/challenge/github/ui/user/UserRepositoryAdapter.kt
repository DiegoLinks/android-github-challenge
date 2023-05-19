package com.challenge.github.ui.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.github.R
import com.challenge.github.model.UserRepository

class UserRepositoryAdapter(
    private val repositoryList: List<UserRepository>
) : RecyclerView.Adapter<UserRepositoryAdapter.UserRepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRepositoryViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_repository, parent, false)
        return UserRepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserRepositoryViewHolder, position: Int) {
        val repository = repositoryList[position]
        holder.bind(repository)
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }

    inner class UserRepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)
        private val tvOwner: TextView = itemView.findViewById(R.id.tv_owner)
        private val tvRepositoryName: TextView = itemView.findViewById(R.id.tv_repository_name)
        private val tvRepositoryDescription: TextView =
            itemView.findViewById(R.id.tv_repository_description)
        private val tvStars: TextView = itemView.findViewById(R.id.tv_stars)
        private val tvLanguage: TextView = itemView.findViewById(R.id.tv_language)

        fun bind(repository: UserRepository) {
            Glide
                .with(itemView.context)
                .load(repository.owner.avatarUrl)
                .circleCrop()
                .placeholder(R.color.black)
                .into(ivAvatar)
            tvOwner.text = repository.owner.login
            tvRepositoryName.text = repository.name
            tvRepositoryDescription.text = repository.description
            tvStars.text = repository.stargazersCount.toString()
            tvLanguage.text = repository.language
        }
    }
}