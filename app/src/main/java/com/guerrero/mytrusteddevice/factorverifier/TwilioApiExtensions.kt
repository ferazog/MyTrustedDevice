package com.guerrero.mytrusteddevice.factorverifier

import com.guerrero.mytrusteddevice.shared.ChallengeStatusWrapper
import com.guerrero.mytrusteddevice.shared.ChallengeWrapper
import com.guerrero.mytrusteddevice.shared.DetailWrapper
import com.guerrero.mytrusteddevice.shared.RegisterInfo
import com.twilio.verify.models.*

fun Factor.toRegisterInfo(): RegisterInfo {
    return RegisterInfo(identity, sid)
}

fun ChallengeList.toChallengeWrapperList(): List<ChallengeWrapper> {
    return this.challenges.map { callenge ->
        callenge.toChallengeWrapper()
    }
}

fun Challenge.toChallengeWrapper() = ChallengeWrapper(
    challengeDetails.message,
    challengeDetails.fields.toDetailWrapper(),
    createdAt.toString(),
    expirationDate.toString(),
    status.toChallengeStatusWrapper()
)

fun List<Detail>.toDetailWrapper() = this.map {
    DetailWrapper(
        it.label,
        it.value
    )
}

fun ChallengeStatus.toChallengeStatusWrapper() = when (this) {
    ChallengeStatus.Pending -> ChallengeStatusWrapper.PENDING
    ChallengeStatus.Approved -> ChallengeStatusWrapper.APPROVED
    ChallengeStatus.Denied -> ChallengeStatusWrapper.DENIED
    ChallengeStatus.Expired -> ChallengeStatusWrapper.EXPIRED
}
