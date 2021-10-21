package com.guerrero.mytrusteddevice.view

import com.guerrero.mytrusteddevice.shared.AccessToken

sealed class ViewState {

    object Normal : ViewState()

    object Loading : ViewState()

    class Error(val message: String) : ViewState()

    class Success(val isDone: Boolean) : ViewState()
}
