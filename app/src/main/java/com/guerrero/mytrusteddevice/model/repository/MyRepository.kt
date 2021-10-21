package com.guerrero.mytrusteddevice.model.repository

import com.guerrero.mytrusteddevice.shared.AccessToken
import com.guerrero.mytrusteddevice.shared.User

interface MyRepository {

    suspend fun getAccessToken(user: User): AccessToken

    suspend fun register(identity: String, factorSid: String): Boolean
}
