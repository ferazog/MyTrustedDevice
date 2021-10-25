package com.guerrero.mytrusteddevice.network

import com.google.gson.annotations.SerializedName
import com.guerrero.mytrusteddevice.shared.AccessToken

data class AccessTokenResponse(
    @SerializedName("token") val token: String,
    @SerializedName("serviceSid") val serviceSid: String,
    @SerializedName("identity") val identity: String,
    @SerializedName("factorType") val factorType: String
) {

    fun toAccessToken() = AccessToken(
        token, serviceSid, identity, factorType
    )
}
