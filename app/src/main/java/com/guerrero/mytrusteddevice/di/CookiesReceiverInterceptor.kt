package com.guerrero.mytrusteddevice.di

import com.guerrero.mytrusteddevice.model.local.LOCAL_STORAGE_COOKIES_KEY
import com.guerrero.mytrusteddevice.model.local.LocalStorageHelper
import okhttp3.Interceptor
import okhttp3.Response

class CookiesReceiverInterceptor(
    private val localStorageHelper: LocalStorageHelper
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            var cookies = ""
            for (cookie in originalResponse.headers("Set-Cookie")) {
                cookies += cookie
            }
            localStorageHelper.saveValue(LOCAL_STORAGE_COOKIES_KEY, cookies)
        }
        return originalResponse
    }
}