package com.guerrero.mytrusteddevice.view

sealed class ViewState {

    object Normal : ViewState()

    object Loading : ViewState()

    class Error(val message: String) : ViewState()

    object Success : ViewState()
}
