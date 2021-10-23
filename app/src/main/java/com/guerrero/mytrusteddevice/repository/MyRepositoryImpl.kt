package com.guerrero.mytrusteddevice.repository

import com.guerrero.mytrusteddevice.network.MyService
import com.guerrero.mytrusteddevice.preferences.LOCAL_STORAGE_KEY_REGISTERED
import com.guerrero.mytrusteddevice.preferences.PreferencesStorage
import com.guerrero.mytrusteddevice.shared.AccessToken
import com.guerrero.mytrusteddevice.shared.RegisterInfo
import com.guerrero.mytrusteddevice.shared.User

class MyRepositoryImpl(
    private val remoteDataSource: MyService,
    private val localDataSource: PreferencesStorage
) : MyRepository {

    override suspend fun getAccessToken(user: User): AccessToken {
        val id = remoteDataSource.login(user.name, user.password).id
        return remoteDataSource.getAccessToken(id).toAccessToken()
    }

    override suspend fun register(registerInfo: RegisterInfo) {
        val isRegistered = remoteDataSource.register(registerInfo.identity, registerInfo.factorSid).done
        localDataSource.save(LOCAL_STORAGE_KEY_REGISTERED, isRegistered)
    }

    override fun isDeviceRegistered(): Boolean {
        return localDataSource.getBoolean(LOCAL_STORAGE_KEY_REGISTERED)
    }
}
