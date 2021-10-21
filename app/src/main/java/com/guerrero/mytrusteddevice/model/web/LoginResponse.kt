package com.guerrero.mytrusteddevice.model.web

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id") val id: String
)
