package com.guerrero.mytrusteddevice.view.challenges

import com.guerrero.mytrusteddevice.shared.ChallengeWrapper

interface CardListener {

    fun onActionClicked(challenge: ChallengeWrapper)
}