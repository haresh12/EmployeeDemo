package com.broadcast.employeedemo.activity.ui.repository

import androidx.lifecycle.LiveData
import com.broadcast.employeedemo.activity.ui.api.RetrofitBuilder
import com.broadcast.employeedemo.activity.ui.model.CreateEmployee
import com.broadcast.employeedemo.activity.ui.model.EmployeeData
import com.broadcast.employeedemo.activity.ui.util.ApiSuccessResponse
import com.broadcast.employeedemo.activity.ui.util.DataState
import com.broadcast.employeedemo.activity.ui.util.GenericApiResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body

object EmployeeRepository {
    fun getEmployeeList(): LiveData<DataState<List<EmployeeData>>> {
        return object : NetworkBoundResource<List<EmployeeData>, List<EmployeeData>>() {
            override fun createCall(): LiveData<GenericApiResponse<List<EmployeeData>>> {
                return RetrofitBuilder.apiService.getEmployees()
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<EmployeeData>>) {
                result.value = DataState.data(
                    null,
                    response.body
                )
            }

        }.asLiveData()
    }

    fun createEmployee(emp: CreateEmployee): LiveData<DataState<ResponseBody>> {
        return object : NetworkBoundResource<ResponseBody, ResponseBody>() {
            override fun createCall(): LiveData<GenericApiResponse<ResponseBody>> {
                return RetrofitBuilder.apiService.createEmployee(emp)
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseBody>) {
                result.value = DataState.data(
                    null,
                    response.body
                )
            }

        }.asLiveData()

    }

    fun updateEmployee(empId: String, emp: CreateEmployee): LiveData<DataState<ResponseBody>> {
        return object : NetworkBoundResource<ResponseBody, ResponseBody>() {
            override fun createCall(): LiveData<GenericApiResponse<ResponseBody>> {
                return RetrofitBuilder.apiService.updateEmployee(empId, emp)
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseBody>) {
                result.value = DataState.data(
                    null,
                    response.body
                )
            }

        }.asLiveData()

    }

    fun deleteEmployee(id: Int): LiveData<DataState<ResponseBody>> {
        return object : NetworkBoundResource<ResponseBody, ResponseBody>() {
            override fun createCall(): LiveData<GenericApiResponse<ResponseBody>> {
                return RetrofitBuilder.apiService.deleteEmployee("$id")
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseBody>) {
                result.value = DataState.data(
                    null,
                    response.body
                )
            }

        }.asLiveData()

    }
}