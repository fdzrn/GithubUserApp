package com.codelab.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codelab.githubuser.R
import com.codelab.githubuser.model.Users
import kotlinx.android.synthetic.main.item_follow.view.*
import kotlinx.android.synthetic.main.item_user.view.avatar_user
import kotlinx.android.synthetic.main.item_user.view.template_id
import kotlinx.android.synthetic.main.item_user.view.template_username

class FollowAdapter : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    private val listFollow = ArrayList<Users>()

    fun setData(items: ArrayList<Users>) {
        listFollow.clear()
        listFollow.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowAdapter.ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_follow, parent, false))

    override fun onBindViewHolder(holder: FollowAdapter.ViewHolder, position: Int) =
        holder.bind(listFollow[position])

    override fun getItemCount(): Int = listFollow.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(users: Users) {
            Glide.with(itemView.context).load(users.photo).into(itemView.avatar_user)
            itemView.template_username.text = users.username
            itemView.template_type.text = users.type
            itemView.template_id.text = users.id.toString()
        }
    }
}