package com.guerrero.mytrusteddevice.view.challenges

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guerrero.mytrusteddevice.factorverifier.FactorVerifier
import com.guerrero.mytrusteddevice.repository.ChallengesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChallengesViewModel @Inject constructor(
    private val repository: ChallengesRepository,
    private val factorVerifier: FactorVerifier
) : ViewModel() {

    private val challengesViewState = MutableLiveData<ChallengesViewState>().apply {
        value = ChallengesViewState.Normal
    }

    fun getChallengesViewStateObservable(): LiveData<ChallengesViewState> = challengesViewState

    private val hideTip = MutableLiveData<Boolean>().apply { value = true }

    fun getHideTipObservable(): LiveData<Boolean> = hideTip

    fun getPendingChallenges() {
        challengesViewState.postValue(ChallengesViewState.Loading)
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val factorSid = repository.getFactorSid()
                    factorVerifier.getPendingChallenges(
                        factorSid,
                        {challenges->
                            if(challenges.isEmpty()) {
                                challengesViewState.postValue(ChallengesViewState.EmptyState)
                            } else {

                            challengesViewState.postValue(ChallengesViewState.Success(challenges))
                            }
                        },
                        { e -> throw e }
                    )
                }
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    private fun handleException(e: Exception) {
        e.printStackTrace()
        challengesViewState.postValue(ChallengesViewState.Error(e.message.toString()))
    }

    fun logout() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.logout()
                    challengesViewState.postValue(ChallengesViewState.Logout)
                }
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun validateHideTip() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val hide = repository.shouldHideTip()
                    hideTip.postValue(hide)
                }
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun dismissTip() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.hideTip()
                }
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }
}
