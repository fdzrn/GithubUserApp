package com.submission.consumerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private val list : ArrayList<DetailUser>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    companion object{
        private const val size = 130
        private const val message = "Sorry, You can only open Detail Page from Github User App"
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
            itemView.card.setOnClickListener { Toast.makeText(itemView.context, message, Toast.LENGTH_LONG).show() }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DetailUser)
    }
}