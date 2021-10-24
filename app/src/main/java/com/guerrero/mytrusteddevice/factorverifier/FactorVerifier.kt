package com.guerrero.mytrusteddevice.factorverifier

import com.guerrero.mytrusteddevice.shared.AccessToken
import com.guerrero.mytrusteddevice.shared.ChallengeWrapper
import com.guerrero.mytrusteddevice.shared.RegisterInfo

interface FactorVerifier {

    fun createFactor(
        accessToken: AccessToken,
        pushToken: String,
        onSuccess: (RegisterInfo) -> Unit,
        onError: (Exception) -> Unit
    )

    fun updatePushToken(token: String)

    fun getPendingChallenges(
        factorSid: String,
        onSuccess: (List<ChallengeWrapper>) -> Unit,
        onError: (Exception) -> Unit
    )

    fun foo(factorSid: String)

    fun getChallenge(
        challengeId: String,
        factorSid: String,
        onSuccess: (ChallengeWrapper) -> Unit,
        onError: (Exception) -> Unit
    )
}
