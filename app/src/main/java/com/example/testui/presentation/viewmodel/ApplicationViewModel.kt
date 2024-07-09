package com.example.testui.presentation.viewmodel

import android.media.tv.SectionResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testui.data.remote.ApiService
import com.example.testui.data.repository.ApplicationRepo
import com.example.testui.model.ApiResponse
import com.example.testui.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private val applicationRepo: ApplicationRepo
) : ViewModel() {

    private val _apiState = MutableStateFlow<NetworkResponse<ApiResponse>>(NetworkResponse.Idle())
    val apiState :StateFlow<NetworkResponse<ApiResponse>> = _apiState.asStateFlow()

    fun getApplications() {
        viewModelScope.launch {
            _apiState.value = NetworkResponse.Loading()
            applicationRepo.getApplications()
                .catch { exception->
                    _apiState.update { NetworkResponse.Error(exception.message.toString()) }
                }
                .collect{response->
                    val body = response.body()
                    if (body != null && body.success) {
                        _apiState.update { NetworkResponse.Success(body) }
                    }else{
                        _apiState.update { NetworkResponse.Error(body?.message.toString()) }
                    }
                }
        }
    }

}