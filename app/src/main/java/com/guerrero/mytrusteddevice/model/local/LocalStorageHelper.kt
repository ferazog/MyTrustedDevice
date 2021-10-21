package com.guerrero.mytrusteddevice.model.local

interface LocalStorageHelper {

    fun getValue(key: String): String

    fun saveValue(key: String, value: String)
}
