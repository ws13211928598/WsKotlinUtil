package com.example.mylibrary.utils

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  created by ws
 *   on 2021/4/7
 *   describe:
 */

class WSRetrofit(){

    fun WsRetrofitinit(url:String):Retrofit{
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    /*Retrofit.Builder()
    .baseUrl("http://api.micaiying.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()
    .create(ApiService::class.java)
    .getNewList()
    .enqueue(object : Callback<RecommendBean>{
        override fun onFailure(call: Call<RecommendBean>, t: Throwable) {

        }

        override fun onResponse(
            call: Call<RecommendBean>,
            response: Response<RecommendBean>
        ) {
            val body = response.body()
            val data = body?.data
            rlv_Recommend.adapter = RecommendRecyclerAdapter((context)!!,data!!)
            rlv_Recommend.layoutManager = LinearLayoutManager((context)!!)
        }

    })*/
    fun <T>WsRetrofitGetData(call: Call<T>):T?{
        var   data :T ?= null
        call.enqueue(object : Callback<T>{
            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.d("WsRetrofitGetData", "onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<T>,
                response: Response<T>
            ) {
                data = response.body()
            }
        })
        return data
    }

}