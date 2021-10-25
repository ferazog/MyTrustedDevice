package com.guerrero.mytrusteddevice.view.register

sealed class RegisterViewState {

    object Normal : RegisterViewState()

    object Loading : RegisterViewState()

    class Error(val message: String) : RegisterViewState()

    object Success : RegisterViewState()
}
