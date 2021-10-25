package com.guerrero.mytrusteddevice.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guerrero.mytrusteddevice.factorverifier.FactorVerifier
import com.guerrero.mytrusteddevice.repository.RegisterRepository
import com.guerrero.mytrusteddevice.shared.RegisterInfo
import com.guerrero.mytrusteddevice.shared.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository,
    private val factorVerifier: FactorVerifier
) : ViewModel() {
    private val registerViewState = MutableLiveData<RegisterViewState>().apply {
        value = RegisterViewState.Normal
    }

    fun getViewStateObservable(): LiveData<RegisterViewState> = registerViewState

    private val isDeviceRegistered = MutableLiveData<Boolean?>().apply { value = null }

    fun getDeviceRegisteredObservable(): LiveData<Boolean?> = isDeviceRegistered

    fun registerUser(user: User, pushToken: String) {
        registerViewState.value = RegisterViewState.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val accessToken = repository.getAccessToken(user)
                factorVerifier.createFactor(
                    accessToken,
                    pushToken,
                    { registerInfo ->
                        callRegisterEndpoint(registerInfo)
                    },
                    { exception -> handleException(exception) }
                )
            }
        }
    }

    private fun callRegisterEndpoint(registerInfo: RegisterInfo) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.register(registerInfo)
                    registerViewState.postValue(RegisterViewState.Success)
                }
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun checkDeviceRegistered() {
        viewModelScope.launch {
            try {
                val registered = withContext(Dispatchers.IO) {
                    repository.isDeviceRegistered()
                }
                isDeviceRegistered.postValue(registered)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    private fun handleException(e: Exception) {
        e.printStackTrace()
        registerViewState.postValue(RegisterViewState.Error(e.message.toString()))
    }
}
