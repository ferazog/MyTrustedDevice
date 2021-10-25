package com.guerrero.mytrusteddevice.repository

import com.guerrero.mytrusteddevice.preferences.LOCAL_STORAGE_KEY_FACTOR_SID
import com.guerrero.mytrusteddevice.preferences.LOCAL_STORAGE_KEY_HIDE_TIP
import com.guerrero.mytrusteddevice.preferences.LOCAL_STORAGE_KEY_REGISTERED
import com.guerrero.mytrusteddevice.preferences.PreferencesStorage

class ChallengesRepositoryImpl(
    private val localDataSource: PreferencesStorage
) : ChallengesRepository {

    override fun getFactorSid(): String {
        return localDataSource.getString(LOCAL_STORAGE_KEY_FACTOR_SID)
    }

    override fun logout() {
        localDataSource.save(LOCAL_STORAGE_KEY_REGISTERED, false)
    }

    override fun shouldHideTip(): Boolean {
        return localDataSource.getBoolean(LOCAL_STORAGE_KEY_HIDE_TIP)
    }

    override fun hideTip() {
        localDataSource.save(LOCAL_STORAGE_KEY_HIDE_TIP, true)
    }
}
