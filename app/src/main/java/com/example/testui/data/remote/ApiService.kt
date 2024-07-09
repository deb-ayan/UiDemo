package com.example.testui.data.remote

import com.example.testui.model.ApiResponse
import com.example.testui.utils.Constants
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST(Constants.APP_LIST)
    @FormUrlEncoded
    suspend fun getApplications(
        @Field("kid_id") id: String
    ): Response<ApiResponse>

}