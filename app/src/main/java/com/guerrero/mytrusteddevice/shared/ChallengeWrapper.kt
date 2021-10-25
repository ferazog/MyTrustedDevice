package com.guerrero.mytrusteddevice.shared

data class ChallengeWrapper(
    val message: String,
    val details: List<DetailWrapper>,
    val date: String,
    val expirationDate: String,
    val status: ChallengeStatusWrapper
) {

    fun formatDetails(): String {
        var result = ""
        details.forEach {
            result = if (result.isEmpty()) {
                "${it.label}: ${it.value}."
            } else {
                "$result ${it.label}: ${it.value}."
            }
        }
        return result
    }
}
