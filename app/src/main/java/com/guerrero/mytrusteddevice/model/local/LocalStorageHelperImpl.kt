package com.guerrero.mytrusteddevice.model.local

import android.content.Context
import com.guerrero.mytrusteddevice.shared.PreferenceNotFoundException

private const val LOCAL_STORAGE_APP_NAME = "com.guerrero.mytrusteddevice"
const val LOCAL_STORAGE_COOKIES_KEY = "cookies"

class LocalStorageHelperImpl(
    private val context: Context
) : LocalStorageHelper {

    override fun getValue(key: String): String {
        return context.getSharedPreferences(LOCAL_STORAGE_APP_NAME, Context.MODE_PRIVATE).getString(key, "")
            ?: throw PreferenceNotFoundException(key)
    }

    override fun saveValue(key: String, value: String) {
        with(context.getSharedPreferences(LOCAL_STORAGE_APP_NAME, Context.MODE_PRIVATE).edit()) {
            putString(key, value)
            apply()
        }
    }
}
