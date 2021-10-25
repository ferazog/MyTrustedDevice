package com.guerrero.mytrusteddevice.di

import com.guerrero.mytrusteddevice.preferences.PreferencesStorage
import com.guerrero.mytrusteddevice.repository.ChallengesRepository
import com.guerrero.mytrusteddevice.repository.ChallengesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ChallengesModule {

    @Provides
    fun provideChallengesRepository(
        preferences: PreferencesStorage
    ): ChallengesRepository {
        return ChallengesRepositoryImpl(preferences)
    }
}
