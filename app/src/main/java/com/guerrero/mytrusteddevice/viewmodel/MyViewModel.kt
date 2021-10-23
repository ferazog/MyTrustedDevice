package com.guerrero.mytrusteddevice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guerrero.mytrusteddevice.factorverifier.FactorVerifier
import com.guerrero.mytrusteddevice.repository.MyRepository
import com.guerrero.mytrusteddevice.shared.RegisterInfo
import com.guerrero.mytrusteddevice.shared.User
import com.guerrero.mytrusteddevice.view.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyViewModel @Inject constructor(
    private val repository: MyRepository,
    private val factorVerifier: FactorVerifier
) : ViewModel() {

    private val viewState = MutableLiveData<ViewState>().apply { value = ViewState.Normal }

    fun getViewStateObservable(): LiveData<ViewState> = viewState

    private val isDeviceRegistered = MutableLiveData<Boolean>().apply { value = false }

    fun getDeviceRegisteredObservable(): LiveData<Boolean> = isDeviceRegistered

    private var factorSid = ""

    fun registerUser(user: User, pushToken: String) {
        viewState.value = ViewState.Loading
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val accessToken = repository.getAccessToken(user)
                    factorVerifier.createFactor(
                        accessToken,
                        pushToken,
                        { registerInfo ->
                            factorSid = registerInfo.factorSid
                            callRegisterEndpoint(registerInfo)
                        },
                        { exception ->
                            throw exception
                        }
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                viewState.postValue(ViewState.Error(e.message.toString()))
            }
        }
    }

    private fun callRegisterEndpoint(registerInfo: RegisterInfo) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.register(registerInfo)
                }
                viewState.postValue(ViewState.Success)
            } catch (e: Exception) {
                e.printStackTrace()
                viewState.postValue(ViewState.Error(e.message.toString()))
            }
        }
    }

    fun getPendingChallenges() {
        factorVerifier.getPendingChallenges(factorSid, {}, {})
    }

    fun checkDeviceRegistered() {
        viewModelScope.launch {
            try {
                val registered = withContext(Dispatchers.IO) {
                    repository.isDeviceRegistered()
                }
                isDeviceRegistered.postValue(registered)
            } catch (e: Exception) {
                e.printStackTrace()
                viewState.postValue(ViewState.Error(e.message.toString()))
            }
        }
    }
}
