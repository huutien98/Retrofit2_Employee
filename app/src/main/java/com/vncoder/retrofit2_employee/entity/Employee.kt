package com.vncoder.retrofit2_employee.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Employee : Serializable {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("employee_name")
    @Expose
    var employee_name: String? = null

    @SerializedName("employee_salary")
    @Expose
    var employee_salary:Int? = null

    @SerializedName("employee_age")
    @Expose
    var employee_age :Int? = null

    @SerializedName("profile_image")
    @Expose
    var profile_image: String?= null
}



