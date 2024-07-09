package com.example.testui.utils

sealed class NetworkResponse<T>(
    val data: T? = null,
    val message: String? = null
){
    class Loading<T> : NetworkResponse<T>()
    class Success<T> (data: T): NetworkResponse<T>(data)
    class Error<T>(msg: String) : NetworkResponse<T>(message = msg)
    class Idle<T> : NetworkResponse<T>()
}