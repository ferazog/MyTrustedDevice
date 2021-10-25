package com.guerrero.mytrusteddevice.preferences

interface PreferencesStorage {

    fun getString(key: String): String

    fun save(key: String, value: String)

    fun getBoolean(key: String): Boolean

    fun save(key: String, value: Boolean)
}
