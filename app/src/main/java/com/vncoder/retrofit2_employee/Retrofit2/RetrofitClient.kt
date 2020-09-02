package com.vncoder.retrofit2_employee.Retrofit2

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {
    private const val BASE_URL = "https://private-amnesiac-8522d-autopilot.apiary-proxy.com/v1/"
//    var retrofit: Retrofit? = null

    private val interceptor = OkHttpClient.Builder()
        .connectTimeout(30,TimeUnit.SECONDS)
        .readTimeout(30,TimeUnit.SECONDS)
        .apply {addInterceptor(MyInterceptor())
    }.build()

//    fun getClient(): Retrofit? {
//            val interceptor = HttpLoggingInterceptor()
//            interceptor.level = HttpLoggingInterceptor.Level.BODY
//            val client = OkHttpClient.Builder()
//                .addNetworkInterceptor(interceptor)
//                .addInterceptor(interceptor)
//                .connectTimeout(0,TimeUnit.SECONDS)
//                .readTimeout(0,TimeUnit.SECONDS)
//                .build()
//            retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//        return retrofit
//
//    }

    val instance: RetrofitApi by lazy {
        val retrofit = Retrofit.Builder()
            .client(interceptor)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(RetrofitApi::class.java)
    }




}

