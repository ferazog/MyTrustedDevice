package com.guerrero.mytrusteddevice.network

import com.guerrero.mytrusteddevice.preferences.LOCAL_STORAGE_KEY_COOKIES
import com.guerrero.mytrusteddevice.preferences.PreferencesStorage
import okhttp3.Interceptor
import okhttp3.Response

private const val HEADER_NAME_COOKIE = "Cookie"

class CookiesGiverInterceptor(
    private val preferencesStorage: PreferencesStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val cookies = preferencesStorage.getString(LOCAL_STORAGE_KEY_COOKIES)
        builder.addHeader(HEADER_NAME_COOKIE, cookies)
        return chain.proceed(builder.build())
    }
}
