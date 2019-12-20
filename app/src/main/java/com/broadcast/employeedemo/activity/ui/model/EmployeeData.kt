package com.broadcast.employeedemo.activity.ui.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmployeeData(
    @SerializedName("id")
    var id: Int = -1,
    @SerializedName("employee_name")
    var name: String = "",
    @SerializedName("employee_salary")
    var salary: String = "",
    @SerializedName("employee_age")
    var age: Int = -1,
    @SerializedName("profile_image")
    var imgProfile: String = ""
)
