package com.broadcast.employeedemo.activity.ui.api

import androidx.lifecycle.LiveData
import com.broadcast.employeedemo.activity.ui.model.CreateEmployee
import com.broadcast.employeedemo.activity.ui.model.EmployeeData
import com.broadcast.employeedemo.activity.ui.util.GenericApiResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("employees")
    fun getEmployees(): LiveData<GenericApiResponse<List<EmployeeData>>>

    @POST("create")
    fun createEmployee(
        @Body employee: CreateEmployee
    ): LiveData<GenericApiResponse<ResponseBody>> //This is how we can directly return the response body

    @PUT("update/{id}")
    fun updateEmployee(
        @Path("id") id: String,
        @Body employee: CreateEmployee
    ): LiveData<GenericApiResponse<ResponseBody>>


    @DELETE("delete/{id}")
    fun deleteEmployee(@Path("id") id: String): LiveData<GenericApiResponse<ResponseBody>>
}