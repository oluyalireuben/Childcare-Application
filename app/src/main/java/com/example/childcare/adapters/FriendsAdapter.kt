package com.example.childcare.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.childcare.R
import com.example.childcare.models.User

class FriendsAdapter (
    private val users : ArrayList<User>,
    private val onFriendClick: OnFriendClick

): RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>(){

    interface OnFriendClick {
        fun onFriendClicked(position: Int)
    }
    inner class FriendsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val textView : TextView = itemView.findViewById(R.id.friend)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        return FriendsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_holder,parent , false))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        holder.textView.text = users[position].name
        holder.itemView.setOnClickListener { onFriendClick.onFriendClicked(position) }
    }
}