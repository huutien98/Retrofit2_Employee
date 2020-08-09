package com.vncoder.retrofit2_employee.Retrofit2

import com.vncoder.retrofit2_employee.entity.Employee
import com.vncoder.retrofit2_employee.entity.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApi {
    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/json"]
    )

    @GET("employees/")
    fun getJsonObject(): Call<JsonObject>

    @POST("create/")
    @FormUrlEncoded
    fun postEmployee(
        @Field("name") name: String,
        @Field("salary") salary: Int?,
        @Field("age") age: Int?
    ): Call<JsonObject>


}