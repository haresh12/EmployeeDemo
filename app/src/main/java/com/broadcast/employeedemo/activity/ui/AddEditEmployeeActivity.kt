package com.broadcast.employeedemo.activity.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.broadcast.employeedemo.R
import com.broadcast.employeedemo.activity.ui.model.CreateEmployee
import com.broadcast.employeedemo.activity.ui.model.EmployeeData
import com.broadcast.employeedemo.activity.ui.util.DataState
import com.broadcast.employeedemo.activity.ui.viewmodel.EmployeeViewModel
import kotlinx.android.synthetic.main.activity_add_edit_employee.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body

class AddEditEmployeeActivity : AppCompatActivity() {
    private lateinit var viewModel: EmployeeViewModel
    private var empData: CreateEmployee? = null
    private var empId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_employee)
        viewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)

        if (intent.hasExtra("age")) {
            val empAge = intent.getStringExtra("age")
            val empName = intent.getStringExtra("name")
            val empSalary = intent.getStringExtra("salary")
            edit_txt_name.setText(empName)
            edit_text_age.setText(empAge)
            edit_text_salary.setText(empSalary)
            btn_add_emp.text = "Edit Employee"
        }
        btn_add_emp.setOnClickListener {
            if (edit_text_age.text.isEmpty() || edit_text_salary.text.isEmpty() || edit_txt_name.text.isEmpty()) {
                Toast.makeText(this, "Fill all required field", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            subScribeObserver()


        }
    }


    private fun subScribeObserver() {
        empData = CreateEmployee(
            age = edit_text_age.text.toString(),
            name = edit_txt_name.text.toString(), salary = edit_text_salary.text.toString()
        )

        if ((intent.hasExtra("age"))) {
            editEmpObserver()
        } else {
            addEmpObserver()
        }
    }

    private fun addEmpObserver() {
        viewModel.createEmployee(empData!!).observe(this,
            Observer<DataState<ResponseBody>> { dataState ->
                if (dataState.loading) {
                    progress_bar.visibility = View.VISIBLE
                } else if (dataState.data != null) {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(this, "Employee added successfully", Toast.LENGTH_LONG)
                        .show()
                    finish()
                } else if (dataState.message != null) {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(this, dataState.message.toString(), Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun editEmpObserver() {
        viewModel.updateEmp(empId.toString(), empData!!).observe(this,
            Observer<DataState<ResponseBody>> { dataState ->
                if (dataState.loading) {
                    progress_bar.visibility = View.VISIBLE
                } else if (dataState.data != null) {
                    progress_bar.visibility = View.GONE
//                    Toast.makeText(this, dataState.data!!.string(), Toast.LENGTH_LONG).show()  use string() not toString
                    //print the response
                    Toast.makeText(this, "Employee edited successfully", Toast.LENGTH_LONG)
                        .show()

                    finish()
                } else if (dataState.message != null) {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(this, dataState.message.toString(), Toast.LENGTH_LONG).show()
                }
            })
    }

}
