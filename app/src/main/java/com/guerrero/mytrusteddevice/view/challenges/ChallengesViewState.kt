package com.guerrero.mytrusteddevice.view.challenges

import com.guerrero.mytrusteddevice.shared.ChallengeWrapper

sealed class ChallengesViewState {

    object Normal : ChallengesViewState()

    object Loading : ChallengesViewState()

    class Error(val message: String) : ChallengesViewState()

    class Success(val challenges: List<ChallengeWrapper>) : ChallengesViewState()

    object Logout : ChallengesViewState()

    object EmptyState: ChallengesViewState()
}
