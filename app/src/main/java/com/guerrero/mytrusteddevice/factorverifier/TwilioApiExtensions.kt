package com.guerrero.mytrusteddevice.factorverifier

import com.guerrero.mytrusteddevice.shared.ChallengeStatusWrapper
import com.guerrero.mytrusteddevice.shared.ChallengeWrapper
import com.guerrero.mytrusteddevice.shared.DetailWrapper
import com.guerrero.mytrusteddevice.shared.RegisterInfo
import com.twilio.verify.models.ChallengeList
import com.twilio.verify.models.ChallengeStatus
import com.twilio.verify.models.Detail
import com.twilio.verify.models.Factor

fun Factor.toRegisterInfo(): RegisterInfo {
    return RegisterInfo(identity, sid)
}

fun ChallengeList.toChallengeWrapperList(): List<ChallengeWrapper> {
    return this.challenges.map {
        it.challengeDetails.message
        ChallengeWrapper(
            it.challengeDetails.message,
            it.challengeDetails.fields.toDetailWrapper(),
            it.createdAt.toString(),
            it.expirationDate.toString(),
            it.status.toChallengeStatusWrapper()
        )
    }
}

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
