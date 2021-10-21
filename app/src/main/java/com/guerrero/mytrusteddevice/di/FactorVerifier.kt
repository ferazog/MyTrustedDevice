package com.guerrero.mytrusteddevice.di

import android.content.Context
import com.twilio.verify.TwilioVerify
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FactorVerifier {

    @Provides
    @Singleton
    fun provideTwilioVerify(@ApplicationContext context: Context): TwilioVerify {
        return TwilioVerify.Builder(context).build()
    }
}