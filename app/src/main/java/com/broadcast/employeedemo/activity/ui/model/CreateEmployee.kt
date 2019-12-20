package com.broadcast.employeedemo.activity.ui.model

import com.google.gson.annotations.SerializedName

data class CreateEmployee(
    @SerializedName("age")
    val age: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("salary")
    val salary: String

)