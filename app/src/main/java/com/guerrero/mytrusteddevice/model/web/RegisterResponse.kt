package com.guerrero.mytrusteddevice.model.web

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("done") val done: Boolean
)
