package com.guerrero.mytrusteddevice.network

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id") val id: String
)
