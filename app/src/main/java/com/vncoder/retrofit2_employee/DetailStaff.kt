package com.vncoder.retrofit2_employee

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vncoder.retrofit2_employee.Model.Employee
import com.vncoder.retrofit2_employee.Retrofit2.RetrofitApi
import com.vncoder.retrofit2_employee.Retrofit2.RetrofitClient
import kotlinx.android.synthetic.main.activity_detail_staff.*
import retrofit2.Call
import retrofit2.Response


class DetailStaff : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_staff)

        var intent = getIntent()
        var employee : Employee = intent?.getSerializableExtra("deatilEmployee") as Employee
        detail_idEmployee.setText(employee.id.toString())
        detail_ageEmployee.setText(employee.employee_age.toString())
        detail_nameEmployee.setText(employee.employee_name.toString())
        detail_salaryEmployee.setText(employee.employee_salary.toString())


        detail_update.setOnClickListener {
            val employee = Employee()
            employee.employee_name =  detail_idEmployee.text.toString()
            employee.employee_salary = detail_salaryEmployee.text.toString().toInt()
            employee.employee_age = detail_ageEmployee.text.toString().toInt()

            RetrofitClient.getClient()?.create(RetrofitApi::class.java)?.updateUser(
                detail_idEmployee.text.toString().toInt(),employee)
                ?.enqueue(object :retrofit2.Callback<Employee>{
                    override fun onFailure(call: Call<Employee>?, t: Throwable?) {
                        Toast.makeText(this@DetailStaff,"oke fail",Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Employee>?, response: Response<Employee>?) {
                        Toast.makeText(this@DetailStaff,"oke response",Toast.LENGTH_LONG).show()
                    }

                })

        }





    }
}