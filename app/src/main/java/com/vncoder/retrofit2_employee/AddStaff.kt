package com.vncoder.retrofit2_employee

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vncoder.retrofit2_employee.Retrofit2.RetrofitApi
import com.vncoder.retrofit2_employee.Retrofit2.RetrofitClient
import com.vncoder.retrofit2_employee.entity.Employee
import com.vncoder.retrofit2_employee.entity.JsonObject
import kotlinx.android.synthetic.main.activity_add_staff.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response


class AddStaff : AppCompatActivity() {
      private var employee : Employee? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_staff)


        btn_create.setOnClickListener {

            val paramObject = JSONObject()
            paramObject.put("name", edt_nameEmployee.text.toString())
            paramObject.put("salary", edt_salaryEmployee.text.toString().toInt())
            paramObject.put("age", edt_ageEmployee.text.toString().toInt())

            RetrofitClient.getClient()?.create(RetrofitApi::class.java)?.postEmployee(edt_nameEmployee.text.toString()
                ,edt_salaryEmployee.text.toString().toInt()
                ,edt_ageEmployee.text.toString().toInt())
                ?.enqueue(object : retrofit2.Callback<JsonObject>{
                override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                    Toast.makeText(this@AddStaff,"failse",Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {

                   Toast.makeText(this@AddStaff,response.toString(),Toast.LENGTH_LONG).show()

                }
            })



//            RetrofitClient?.instance.postEmployee(edt_nameEmployee.text.toString(),edt_salaryEmployee.text.toString().toInt(),edt_ageEmployee.text.toString().toInt())
//                .enqueue(object : retrofit2.Callback<JsonObject> {
//            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
//                Toast.makeText(this@AddStaff,"Add failure",Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
//                if (response!!.isSuccessful){
//                    Toast.makeText(this@AddStaff, "Save success", Toast.LENGTH_LONG).show()
//                    val intent  = Intent()
//                    setResult(Activity.RESULT_OK, intent)
//                    finish()
//                }else{
//                    Toast.makeText(this@AddStaff,"Add failure",Toast.LENGTH_LONG).show()
//                }
//            }
//        })

        }

    }
}