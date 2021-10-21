package com.guerrero.mytrusteddevice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guerrero.mytrusteddevice.model.repository.MyRepository
import com.guerrero.mytrusteddevice.shared.AccessToken
import com.guerrero.mytrusteddevice.shared.User
import com.guerrero.mytrusteddevice.view.ViewState
import com.twilio.verify.TwilioVerify
import com.twilio.verify.models.Factor
import com.twilio.verify.models.PushFactorPayload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyViewModel @Inject constructor(
    private val repository: MyRepository,
    private val twilioVerify: TwilioVerify
) : ViewModel() {

    private val viewState = MutableLiveData<ViewState>()

    fun getViewStateObservable(): LiveData<ViewState> = viewState

    fun registerUser(user: User, pushToken: String) {
        viewState.value = ViewState.Loading
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val accessToken = repository.getAccessToken(user)
                    val factorPayload = buildFactorPayload(accessToken, pushToken)
                    twilioVerify.createFactor(
                        factorPayload,
                        { factor ->
                            callRegisterEndpoint(factor)
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

    private fun buildFactorPayload(
        accessToken: AccessToken,
        pushToken: String
    ): PushFactorPayload {
        return accessToken.run {
            PushFactorPayload(
                friendlyName = android.os.Build.MODEL,
                serviceSid = serviceSid,
                identity = identity,
                pushToken = pushToken,
                accessToken = token
            )
        }
    }

    private fun callRegisterEndpoint(factor: Factor) {
        viewModelScope.launch {
            try {
                val isDone = withContext(Dispatchers.IO) {
                    repository.register(factor.identity, factor.sid)
                }
                viewState.postValue(ViewState.Success(isDone))
            } catch (e: Exception) {
                e.printStackTrace()
                viewState.postValue(ViewState.Error(e.message.toString()))
            }
        }
    }
}
