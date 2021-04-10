package com.example.mylibrary.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.assist.WsViewHolder

/**
 *  created by ws
 *   on 2021/4/7
 *   describe:
 */
 abstract class WsRecyclerAdapterMore (var context: Context,var layout:MutableList<Int>): RecyclerView.Adapter<WsViewHolder>() {


    override fun getItemViewType(position: Int): Int {
        return WsgetItemViewType(position)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WsViewHolder {
        return WsViewHolder(LayoutInflater.from(context).inflate(layout[viewType],parent,false))
    }

    override fun getItemCount(): Int {
        return WsItemCount()
    }

    override fun onBindViewHolder(holder: WsViewHolder, position: Int) {
      WsBindViewHolder(holder,position)
    }
    abstract fun WsItemCount():Int
    abstract fun WsBindViewHolder(holder: WsViewHolder, position: Int)
    abstract fun WsgetItemViewType(position: Int):Int

}
