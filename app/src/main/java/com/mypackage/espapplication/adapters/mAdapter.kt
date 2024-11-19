package com.mypackage.espapplication.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.RecyclerView
import com.mypackage.espapplication.R
import com.mypackage.espapplication.models.Profile

class mAdapter(private val list : ArrayList<Profile>) : RecyclerView.Adapter<mAdapter.ViewHolder>() {
    private lateinit var mListener : onProfileButtonClickListener

    interface onProfileButtonClickListener{
        fun onProfileClick(position: Int)
    }
    fun setOnProfileClickListener(clickListener: onProfileButtonClickListener){
        mListener = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.profile_item,parent,false)
        return ViewHolder(itemView,mListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: mAdapter.ViewHolder, position: Int) {
        val currentProfile = list[position]
        holder.tvSingleProfileName.text=currentProfile.name
    }
    class ViewHolder(itemView : View,clickListener: onProfileButtonClickListener) : RecyclerView.ViewHolder(itemView){
        val tvSingleProfileName : TextView = itemView.findViewById(R.id.profileNameSingleItem)
        val selectProfileButton : Button = itemView.findViewById(R.id.selectProfileButton)
        init{
            selectProfileButton.setOnClickListener{
                clickListener.onProfileClick(absoluteAdapterPosition)
            }
        }
    }
}