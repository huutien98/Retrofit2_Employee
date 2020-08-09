package com.vncoder.retrofit2_employee

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vncoder.retrofit2_employee.Adapter.EmployeeAdapter
import com.vncoder.retrofit2_employee.Retrofit2.RetrofitClient
import com.vncoder.retrofit2_employee.entity.Employee
import com.vncoder.retrofit2_employee.entity.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class ListStaff : AppCompatActivity() {

    private val ActivityRequestCode = 1

    private var list = ArrayList<Employee>()
    private var jsonObject : JsonObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var rv_RecyclerView = findViewById<RecyclerView>(R.id.rv_recyclerView)

        rv_RecyclerView.setHasFixedSize(true)
        rv_RecyclerView.layoutManager = LinearLayoutManager(this)

        RetrofitClient.instance.getJsonObject().enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                Toast.makeText(this@ListStaff,"onFailure",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>) {
                jsonObject = response.body()
                if (response.isSuccessful) {
                    list = jsonObject?.data as ArrayList<Employee>
                    rv_RecyclerView.adapter = EmployeeAdapter(list)

                } else {
                    Toast.makeText(
                        applicationContext,
                        "not data " + response.code().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        })
        sw_refresh.setOnRefreshListener {getData()
            sw_refresh.isRefreshing = false
        }

        btn_addStaff.setOnClickListener {
            var intent = Intent(this,AddStaff::class.java)
            startActivityForResult(intent,ActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ActivityRequestCode && resultCode == Activity.RESULT_OK) {
            getData()
            Toast.makeText(this,"data update sucess",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"No new data available",Toast.LENGTH_LONG).show()
        }
    }



    fun getData(){
        RetrofitClient.instance.getJsonObject().enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                Toast.makeText(this@ListStaff,"onFailure",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>) {
                jsonObject = response.body()
                if (response.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        " data show " + response.code().toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(
                        applicationContext,
                        "not data " + response.code().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        })
        sw_refresh.isRefreshing = false
    }

}