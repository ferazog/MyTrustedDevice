package com.guerrero.mytrusteddevice.network

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("done") val done: Boolean
)
