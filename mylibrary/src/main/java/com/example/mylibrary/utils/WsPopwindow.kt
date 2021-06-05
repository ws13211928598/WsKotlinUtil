package com.example.mylibrary.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow

abstract class WsPopwindow(context: Context, var viewGroup: View, layout:Int){
     
    var popupWindow:PopupWindow 
    init {
        val inflate = LayoutInflater.from(context).inflate(layout, null)
         popupWindow = PopupWindow(
            inflate,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )
        
    }
    fun show(){
        setView(popupWindow)
        popupWindow.showAsDropDown(viewGroup)
    }
    abstract fun setView(popupWindow: PopupWindow)
    
}