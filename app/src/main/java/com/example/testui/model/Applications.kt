package com.example.testui.model

import androidx.annotation.DrawableRes

data class Applications(
    val appName: String,
    @DrawableRes val appIcon: Int,
    var isConnected: Boolean
)
