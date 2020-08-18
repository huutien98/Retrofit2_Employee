package com.vncoder.retrofit2_employee.Retrofit2

import com.vncoder.retrofit2_employee.Model.Employee
import com.vncoder.retrofit2_employee.Model.JsonObject
import okhttp3.ResponseBody
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
        @Field("age") age: Int?,
        @Field("id") id: Int?
    ): Call<JsonObject>

    @DELETE("delete/{id}")
    open fun deleteBook(
        @Path("id") id: Int
    ): Call<Employee>

    @PUT("update/{id}")
    open fun updateUser(
        @Path("id") id: Int,
        @Body body: Employee?
    ): Call<Employee>

}