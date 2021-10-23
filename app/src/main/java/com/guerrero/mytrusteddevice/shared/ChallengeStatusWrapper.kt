package com.guerrero.mytrusteddevice.shared

import com.guerrero.mytrusteddevice.R

enum class ChallengeStatusWrapper(val stringResource: Int) {
    PENDING(R.string.pending),
    APPROVED(R.string.approved),
    DENIED(R.string.denied),
    EXPIRED(R.string.expired)
}
