package com.example.mylibrary.utils

import android.content.Context
import android.content.SharedPreferences

/**
 *  created by ws
 *   on 2021/3/31
 *   describe:
 */
    var  sps: SharedPreferences ?= null
    private fun getSps(context: Context): SharedPreferences {
        if (sps == null) {
            sps = context.getSharedPreferences("default", Context.MODE_PRIVATE)
        }
        return sps!!
    }

    fun putString(key: String, value: String, context: Context) {
        var editor: SharedPreferences.Editor = getSps(context).edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String, context: Context): String? {
        var sps: SharedPreferences = getSps(context)
        return sps.getString(key, " ")
    }
