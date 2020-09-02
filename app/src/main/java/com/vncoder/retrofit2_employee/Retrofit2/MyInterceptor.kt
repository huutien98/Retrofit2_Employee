package com.vncoder.retrofit2_employee.Retrofit2

import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Accept","application/json")
            .addHeader("Content","type:application/json")
            .addHeader("autopilotapikey","5371d36a66ae4755bc5d4d595397b965")
            .build()
        return chain.proceed(request)
    }

}