package com.vncoder.retrofit2_employee.Retrofit2

import com.vncoder.retrofit2_employee.Model.*
import retrofit2.Call
import retrofit2.http.*


interface RetrofitApi {
    @GET("contacts/bookmark")
    fun getdata(): Call<JsonObject>

    @POST("contact")
    fun postContact(
    @Body
    ContacCreate : ContactCreate
    ): Call<ContactCreate>

    @DELETE("contact/{contact_id}")
    fun deleteContact(
        @Path("contact_id") contact_id: String
    ): Call<Contact>



}

