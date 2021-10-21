package com.guerrero.mytrusteddevice.di

import com.guerrero.mytrusteddevice.model.local.LOCAL_STORAGE_COOKIES_KEY
import com.guerrero.mytrusteddevice.model.local.LocalStorageHelper
import okhttp3.Interceptor
import okhttp3.Response

class CookiesGiverInterceptor(
    private val localStorageHelper: LocalStorageHelper
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val cookies = localStorageHelper.getValue(LOCAL_STORAGE_COOKIES_KEY)
        builder.addHeader("Cookie", cookies)
        return chain.proceed(builder.build())
    }
}
