package com.guerrero.mytrusteddevice.model.repository

import com.google.gson.annotations.SerializedName
import com.guerrero.mytrusteddevice.model.web.MyService
import com.guerrero.mytrusteddevice.shared.AccessToken
import com.guerrero.mytrusteddevice.shared.User

class MyRepositoryImpl(
    private val service: MyService
) : MyRepository {

    override suspend fun getAccessToken(user: User): AccessToken {
        val id = service.login(user.name, user.password).id
        return service.getAccessToken(id).toAccessToken()
    }

    override suspend fun register(identity: String, factorSid: String): Boolean {
        return service.register(identity, factorSid).done
    }
}
