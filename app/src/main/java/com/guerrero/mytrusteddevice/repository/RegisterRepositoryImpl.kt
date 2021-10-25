package com.guerrero.mytrusteddevice.repository

import com.guerrero.mytrusteddevice.network.RegisterService
import com.guerrero.mytrusteddevice.preferences.LOCAL_STORAGE_KEY_FACTOR_SID
import com.guerrero.mytrusteddevice.preferences.LOCAL_STORAGE_KEY_REGISTERED
import com.guerrero.mytrusteddevice.preferences.PreferencesStorage
import com.guerrero.mytrusteddevice.shared.AccessToken
import com.guerrero.mytrusteddevice.shared.RegisterInfo
import com.guerrero.mytrusteddevice.shared.User

class RegisterRepositoryImpl(
    private val remoteDataSource: RegisterService,
    private val localDataSource: PreferencesStorage
) : RegisterRepository {

    override suspend fun getAccessToken(user: User): AccessToken {
        val id = remoteDataSource.login(user.name, user.password).id
        return remoteDataSource.getAccessToken(id).toAccessToken()
    }

    override suspend fun register(registerInfo: RegisterInfo) {
        val isRegistered = registerInfo.run {
            remoteDataSource.register(identity, factorSid).done
        }
        localDataSource.save(LOCAL_STORAGE_KEY_REGISTERED, isRegistered)
        localDataSource.save(LOCAL_STORAGE_KEY_FACTOR_SID, registerInfo.factorSid)
    }

    override fun isDeviceRegistered(): Boolean {
        return localDataSource.getBoolean(LOCAL_STORAGE_KEY_REGISTERED)
    }
}
