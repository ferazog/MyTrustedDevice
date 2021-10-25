package com.guerrero.mytrusteddevice.preferences

import android.content.Context

class PreferencesStorageImpl(
    private val context: Context
) : PreferencesStorage {

    override fun getString(key: String): String {
        return context.getSharedPreferences(LOCAL_STORAGE_APP_NAME, Context.MODE_PRIVATE)
            .getString(key, "").toString()
    }

    override fun save(key: String, value: String) {
        with(context.getSharedPreferences(LOCAL_STORAGE_APP_NAME, Context.MODE_PRIVATE).edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun save(key: String, value: Boolean) {
        with(context.getSharedPreferences(LOCAL_STORAGE_APP_NAME, Context.MODE_PRIVATE).edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    override fun getBoolean(key: String): Boolean {
        return context.getSharedPreferences(LOCAL_STORAGE_APP_NAME, Context.MODE_PRIVATE)
            .getBoolean(key, false)
    }
}
