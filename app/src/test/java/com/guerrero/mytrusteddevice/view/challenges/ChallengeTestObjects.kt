package com.guerrero.mytrusteddevice.view.challenges

import com.guerrero.mytrusteddevice.shared.ChallengeStatusWrapper
import com.guerrero.mytrusteddevice.shared.ChallengeWrapper
import com.guerrero.mytrusteddevice.shared.DetailWrapper

object ChallengeTestObjects {

    val CHALLENGES = listOf(
        ChallengeWrapper(
            message = "message",
            details = listOf(
                DetailWrapper(
                    label = "label",
                    value = "value"
                )
            ),
            date = "01/01/2022",
            expirationDate = "02/02/2022",
            status = ChallengeStatusWrapper.PENDING
        )
    )
}
