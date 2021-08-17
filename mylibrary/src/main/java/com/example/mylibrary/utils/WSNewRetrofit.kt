package com.example.mylibrary.utils

import androidx.viewbinding.BuildConfig
import com.dlg.network.interceptor.GlobalHeaderInterceptor
import com.dlg.network.interceptor.GlobalUrlInterceptor
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  created by ws
 *   on 2021/8/17
 *   describe:
 */

var RetrofitWsUrl = ""

val okHttpClient:OkHttpClient by lazy {
     OkHttpClient
        .Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectionPool(ConnectionPool(5, 5, TimeUnit.MINUTES))
        .retryOnConnectionFailure(true) // 连接失败后是否重新连接
        .followRedirects(true) // 302重定向
        .addInterceptor(GlobalHeaderInterceptor())
        //.addInterceptor(logging)
       // .addInterceptor(GlobalUrlInterceptor())
         .build()
}



fun <T>RetrofitWs(RetrofitWsUrl:String,service:Class<T>,map: HashMap<String, Any>):T{
    return Retrofit
        .Builder()
        .baseUrl(RetrofitWsUrl)
        .client(okHttpClient)
        //.addConverterFactory(NullOnEmptyConverterFactory.create())
        //.addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        //.addConverterFactory(FastJsonConverterFactory.create())
        //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addCallAdapterFactory(FlowCallAdapterFactory.create())
        .build()
        .create(service)
}
fun <T>RetrofitWs(RetrofitWsUrl:String,okHttpClient:OkHttpClient,service:Class<T>,map: HashMap<String, Any>):T{
    return Retrofit
        .Builder()
        .baseUrl(RetrofitWsUrl)
        .client(okHttpClient)
        //.addConverterFactory(NullOnEmptyConverterFactory.create())
        //.addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        //.addConverterFactory(FastJsonConverterFactory.create())
        //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addCallAdapterFactory(FlowCallAdapterFactory.create())
        .build()
        .create(service)
}