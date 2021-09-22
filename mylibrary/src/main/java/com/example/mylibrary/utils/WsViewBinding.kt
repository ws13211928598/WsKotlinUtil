package com.example.mylibrary.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KProperty

/**
 *  created by ws
 *   on 2021/6/18
 *   describe:
 */
inline fun <reified VB:ViewBinding> RecyclerView.ViewHolder.viewBinding(crossinline black:(View)->VB) = black.invoke(this.itemView)
inline fun <reified VB:ViewBinding> AppCompatActivity.viewBinding() = DelegateBinding(VB::class.java)
class DelegateBinding<VB: ViewBinding>(val cls:Class<VB>) {
    private var binding:VB ?= null

    operator fun getValue(activity: AppCompatActivity, property: KProperty<*>):VB{
        if(binding ==null){
            val declaredMethod = cls.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java
            )
            binding= declaredMethod.invoke(null,activity.layoutInflater) as VB
            activity.setContentView(binding!!.root)
        }
        return  binding!!
    }
}