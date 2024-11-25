package com.mypackage.espapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mypackage.espapplication.R
import com.mypackage.espapplication.models.Profile

class mAdapter(private val list : ArrayList<Profile>) : RecyclerView.Adapter<mAdapter.ViewHolder>() {
    private lateinit var mButtonListener : OnProfileButtonClickListener
    private lateinit var mViewListener : OnProfileViewClickListener

    interface OnProfileViewClickListener{
        fun onProfileViewClick(position: Int)
    }

    interface OnProfileButtonClickListener{
        fun onButtonProfileClick(position: Int)
    }

    fun setOnProfileButtonClickListener(clickListener: OnProfileButtonClickListener){
        mButtonListener = clickListener
    }

    fun setOnProfileViewClickListener(clickListener: OnProfileViewClickListener){
        mViewListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.profile_item,parent,false)
        return ViewHolder(itemView,mButtonListener,mViewListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: mAdapter.ViewHolder, position: Int) {
        val currentProfile = list[position]
        holder.tvSingleProfileName.text=currentProfile.name
    }
    class ViewHolder(itemView : View, clickButtonListener: OnProfileButtonClickListener, clickViewListener : OnProfileViewClickListener) : RecyclerView.ViewHolder(itemView){
        val tvSingleProfileName : TextView = itemView.findViewById(R.id.profileNameSingleItem)
        private val selectProfileButton : Button = itemView.findViewById(R.id.selectProfileButton)
        init{
            selectProfileButton.setOnClickListener{
                clickButtonListener.onButtonProfileClick(absoluteAdapterPosition)
            }
            tvSingleProfileName.setOnClickListener{
                clickViewListener.onProfileViewClick(absoluteAdapterPosition)
            }
        }
    }
}