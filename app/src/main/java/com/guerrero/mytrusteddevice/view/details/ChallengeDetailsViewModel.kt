package com.guerrero.mytrusteddevice.view.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guerrero.mytrusteddevice.factorverifier.FactorVerifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChallengeDetailsViewModel @Inject constructor(
    private val factorVerifier: FactorVerifier
) : ViewModel() {

    private val detailsViewState = MutableLiveData<DetailsViewState>()

    fun getDetailsViewStateObservable(): LiveData<DetailsViewState> = detailsViewState

    private var challengeId = ""

    private var factorSid = ""

    fun getDetails(challengeId: String, factorSid: String) {
        this.challengeId = challengeId
        this.factorSid = factorSid
        detailsViewState.postValue(DetailsViewState.Loading)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                factorVerifier.getChallenge(
                    challengeId, factorSid,
                    { challenge ->
                        detailsViewState.postValue(
                            DetailsViewState.Success(challenge)
                        )
                    },
                    { exception -> handleException(exception) }
                )
            }
        }
    }

    fun approve() {
        viewModelScope.launch {
            detailsViewState.postValue(DetailsViewState.Loading)
            factorVerifier.approveChallenge(challengeId, factorSid,
                {
                    detailsViewState.postValue(DetailsViewState.ChallengeUpdated)
                },
                { exception ->
                    handleException(exception)
                }
            )
        }
    }

    fun deny() {
        viewModelScope.launch {
            detailsViewState.postValue(DetailsViewState.Loading)
            factorVerifier.denyChallenge(challengeId, factorSid,
                {
                    detailsViewState.postValue(DetailsViewState.ChallengeUpdated)
                },
                { exception ->
                    handleException(exception)
                }
            )
        }
    }

    private fun handleException(e: Exception) {
        e.printStackTrace()
        detailsViewState.postValue(DetailsViewState.Error(e.message.toString()))
    }
}
