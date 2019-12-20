package com.broadcast.employeedemo.activity.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.broadcast.employeedemo.activity.ui.model.CreateEmployee
import com.broadcast.employeedemo.activity.ui.model.EmployeeData
import com.broadcast.employeedemo.activity.ui.repository.EmployeeRepository
import com.broadcast.employeedemo.activity.ui.util.DataState
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body

class EmployeeViewModel : ViewModel() {
    private var empList = MutableLiveData<DataState<List<EmployeeData>>>()

    init {
        employeeList()
    }

    private fun employeeList() {
        empList =
            EmployeeRepository.getEmployeeList() as MutableLiveData<DataState<List<EmployeeData>>>

    }

    fun getEmployeeList(): LiveData<DataState<List<EmployeeData>>> {
        return empList
    }

    fun createEmployee(emp: CreateEmployee): LiveData<DataState<ResponseBody>> {
        return EmployeeRepository.createEmployee(emp)
    }

    fun updateEmp(empId: String, emp: CreateEmployee): LiveData<DataState<ResponseBody>> {
        return EmployeeRepository.updateEmployee(empId, emp)
    }

    fun deleteEmployee(id: Int): LiveData<DataState<ResponseBody>> {
        return EmployeeRepository.deleteEmployee(id)
    }


}

