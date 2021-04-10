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
 abstract class WsRecyclerAdapterSingle (var context: Context,var layout:Int): RecyclerView.Adapter<WsViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):WsViewHolder {
        val inflate = LayoutInflater.from(context).inflate(layout, parent, false)
        return WsViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return WsItemCount()
    }

    override fun onBindViewHolder(holder: WsViewHolder, position: Int) {
        WsBindViewHolder(holder,position)
    }

    abstract fun WsItemCount():Int
    abstract fun WsBindViewHolder(holder: WsViewHolder, position: Int)
}
