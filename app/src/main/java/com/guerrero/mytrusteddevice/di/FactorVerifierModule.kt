package com.guerrero.mytrusteddevice.di

import android.content.Context
import com.guerrero.mytrusteddevice.factorverifier.FactorVerifier
import com.guerrero.mytrusteddevice.factorverifier.TwilioFactorVerifier
import com.twilio.verify.TwilioVerify
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FactorVerifierModule {

    @Provides
    @Singleton
    fun provideTwilioFactorVerifier(@ApplicationContext context: Context): FactorVerifier {
        return TwilioFactorVerifier(
            TwilioVerify.Builder(context).build()
        )
    }
}
