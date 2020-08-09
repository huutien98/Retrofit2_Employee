package com.vncoder.retrofit2_employee.Retrofit2

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "http://dummy.restapiexample.com/api/v1/"


    var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        return retrofit

    }


//      val okHttpClient = OkHttpClient.Builder().addInterceptor(object : Interceptor{
//        override fun intercept(chain: Interceptor.Chain): Response {
//            var original : Request = chain.request()
//            var request : Request = original.newBuilder()
//                .header("Content-Type","application/json")
//                .header("Accept","application/json")
//                .method(original.method,original.body)
//                .build()
//
//            return chain.proceed(request)
//        }
//
//    })


    val instance: RetrofitApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(RetrofitApi::class.java)
    }

}

