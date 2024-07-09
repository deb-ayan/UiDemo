package com.example.testui.data.repository

import com.example.testui.data.remote.ApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApplicationRepo @Inject constructor(
    private val apiService: ApiService
) {

    fun getApplications() = flow { emit(apiService.getApplications("378")) }


}