package com.guerrero.mytrusteddevice.shared

data class ChallengeWrapper(
    val message: String,
    val details: List<DetailWrapper>,
    val date: String,
    val expirationDate: String,
    val status: ChallengeStatusWrapper
)
