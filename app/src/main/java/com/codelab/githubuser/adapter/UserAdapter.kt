package com.codelab.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codelab.githubuser.R
import com.codelab.githubuser.model.DetailUser
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private val list : ArrayList<DetailUser>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: List<DetailUser>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))


    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        fun bind(user:DetailUser) {
            Glide.with(itemView.context).load(user.photoProfile).into(itemView.avatar_user)
            itemView.template_username.text = user.username
            itemView.template_id.text = user.id.toString()
            itemView.template_location.text = user.location
            itemView.card.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DetailUser)
    }
}