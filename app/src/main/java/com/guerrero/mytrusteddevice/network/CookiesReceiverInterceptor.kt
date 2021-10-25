package com.guerrero.mytrusteddevice.network

import com.guerrero.mytrusteddevice.preferences.LOCAL_STORAGE_KEY_COOKIES
import com.guerrero.mytrusteddevice.preferences.PreferencesStorage
import okhttp3.Interceptor
import okhttp3.Response

private const val HEADER_NAME_SET_COOKIE = "Set-Cookie"

class CookiesReceiverInterceptor(
    private val preferencesStorage: PreferencesStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.headers(HEADER_NAME_SET_COOKIE).isNotEmpty()) {
            var cookies = ""
            for (cookie in originalResponse.headers(HEADER_NAME_SET_COOKIE)) {
                cookies += cookie
            }
            preferencesStorage.save(LOCAL_STORAGE_KEY_COOKIES, cookies)
        }
        return originalResponse
    }
}
