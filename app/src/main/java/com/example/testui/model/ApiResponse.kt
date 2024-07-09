package com.example.testui.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("data") val info: Data,
    val message: String,
    val success: Boolean
)