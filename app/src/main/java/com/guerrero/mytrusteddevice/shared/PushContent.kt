package com.guerrero.mytrusteddevice.shared

data class PushContent(
    val challengeId: String,
    val factorSid: String,
    val title: String,
    val message: String
)
