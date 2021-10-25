package com.guerrero.mytrusteddevice.view.details

import com.guerrero.mytrusteddevice.shared.ChallengeWrapper

sealed class DetailsViewState {

    object Loading : DetailsViewState()

    class Success(val challenge: ChallengeWrapper): DetailsViewState()

    class Error(val message: String): DetailsViewState()

    object ChallengeUpdated: DetailsViewState()
}
