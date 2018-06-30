package com.gkgio.vkfriendsviewer.ui.main

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gkgio.vkfriendsviewer.R
import com.gkgio.vkfriendsviewer.data.model.FriendInfo
import java.util.ArrayList

class RecyclerFriendsAdapter : RecyclerView.Adapter<RecyclerFriendsAdapter.FriendsItemViewHolder>() {
  companion object {
    private val TAG = RecyclerFriendsAdapter::class.java.simpleName
  }

  private var friendInfoList: List<FriendInfo>? = null

  init {
    this.friendInfoList = ArrayList()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsItemViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.friends_adapter_holder_item, parent, false)
    return FriendsItemViewHolder(view)
  }

  fun setDocuments(friendInfoList: List<FriendInfo>?) {
    this.friendInfoList = friendInfoList
    notifyDataSetChanged()
  }

  override fun onBindViewHolder(holder: FriendsItemViewHolder, position: Int) {
    val friendInfo = friendInfoList
    if (friendInfo != null) {

    }
  }

  override fun getItemCount(): Int {
    return friendInfoList?.size ?: 0
  }

  // view holder class ======================
  class FriendsItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.textViewName)
    val photo:AppCompatImageView = itemView.findViewById(R.id.userPhoto)
  }
}