package com.guerrero.mytrusteddevice.shared

data class AccessToken(
    val token: String,
    val serviceSid: String,
    val identity: String,
    val factorType: String
)
