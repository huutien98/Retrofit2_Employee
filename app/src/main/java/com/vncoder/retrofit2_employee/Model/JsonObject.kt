package com.vncoder.retrofit2_employee.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class JsonObject : Serializable{
    @SerializedName("status")
    @Expose
    val status: String? = null

    @SerializedName("data")
    @Expose
    var data: List<Employee>? = null

    @SerializedName("message")
    @Expose
    var message: String?= null
}



