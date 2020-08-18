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
import com.vncoder.retrofit2_employee.Model.Employee
import com.vncoder.retrofit2_employee.Model.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class ListStaff : AppCompatActivity() {
    private val ActivityRequestDetail = 2
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
                    rv_RecyclerView.adapter = EmployeeAdapter(onClickListener,list)

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


    private var onClickListener = object : EmployeeAdapter.OnItemClickListener{
        override fun onClickEmployee(employee: Employee) {
            val replyintent = Intent(this@ListStaff,DetailStaff::class.java)
            replyintent.putExtra("deatilEmployee",employee)
            startActivity(replyintent)
        }

        override fun onLongClickEmployee(employee: Employee) {
            val builder = androidx.appcompat.app.AlertDialog.Builder(this@ListStaff)
            builder.setTitle("notification")
            builder.setMessage("Do you want delete all item ?")
            builder.setCancelable(false)
            builder.setPositiveButton("Yes")
            { dialogInterface, i ->
                RetrofitClient.instance.deleteBook(employee.id!!.toInt())?.enqueue(object : retrofit2.Callback<Employee> {
                    override fun onFailure(call: Call<Employee>?, t: Throwable?) {
                         Toast.makeText(this@ListStaff,"fail",Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Employee>?, response: Response<Employee>) {
                        Toast.makeText(this@ListStaff,"success",Toast.LENGTH_LONG).show()
                    }
                })

                }
            builder.setNegativeButton("No")
            { dialogInterface, i -> dialogInterface.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }

    }




}