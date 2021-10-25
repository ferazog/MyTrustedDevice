package com.guerrero.mytrusteddevice.di

import android.content.Context
import com.guerrero.mytrusteddevice.network.CookiesGiverInterceptor
import com.guerrero.mytrusteddevice.network.CookiesReceiverInterceptor
import com.guerrero.mytrusteddevice.preferences.PreferencesStorage
import com.guerrero.mytrusteddevice.preferences.PreferencesStorageImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "http://10.0.2.2:5000/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideHttpClient(
        cookiesReceiverInterceptor: CookiesReceiverInterceptor,
        cookiesGiverInterceptor: CookiesGiverInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(cookiesGiverInterceptor)
            .addInterceptor(cookiesReceiverInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    }

    @Provides
    fun provideCookieReceiverInterceptor(preferencesStorage: PreferencesStorage): CookiesReceiverInterceptor {
        return CookiesReceiverInterceptor(preferencesStorage)
    }

    @Provides
    fun provideCookieGiverInterceptor(preferencesStorage: PreferencesStorage): CookiesGiverInterceptor {
        return CookiesGiverInterceptor(preferencesStorage)
    }

    @Provides
    fun provideLocalStorage(
        @ApplicationContext context: Context
    ): PreferencesStorage {
        return PreferencesStorageImpl(context)
    }
}
