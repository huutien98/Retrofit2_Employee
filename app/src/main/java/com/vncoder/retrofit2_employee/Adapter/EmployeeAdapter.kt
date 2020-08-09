package com.vncoder.retrofit2_employee.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vncoder.retrofit2_employee.R
import com.vncoder.retrofit2_employee.entity.Employee

class EmployeeAdapter(private val list: ArrayList<Employee>):
    RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val idEmployee= itemView.findViewById<TextView>(R.id.id_employee)
        val nameEmployee = itemView.findViewById<TextView>(R.id.name_employee)
        val ageEmployee = itemView.findViewById<TextView>(R.id.age_employee)
        val salaryEmployee = itemView.findViewById<TextView>(R.id.salary_employee)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_employee,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemEmployee = list[position]
        holder.idEmployee.setText(itemEmployee.id.toString())
        holder.nameEmployee.setText(itemEmployee.employee_name).toString()
        holder.ageEmployee.setText(itemEmployee.employee_age.toString())
        holder.salaryEmployee.setText(itemEmployee.employee_salary.toString())
    }
}