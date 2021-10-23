package com.guerrero.mytrusteddevice.repository

import com.guerrero.mytrusteddevice.shared.AccessToken
import com.guerrero.mytrusteddevice.shared.RegisterInfo
import com.guerrero.mytrusteddevice.shared.User

interface MyRepository {

    suspend fun getAccessToken(user: User): AccessToken

    suspend fun register(registerInfo: RegisterInfo)

    fun isDeviceRegistered(): Boolean
}
