package com.guerrero.mytrusteddevice.di

import com.guerrero.mytrusteddevice.network.RegisterService
import com.guerrero.mytrusteddevice.preferences.PreferencesStorage
import com.guerrero.mytrusteddevice.repository.RegisterRepository
import com.guerrero.mytrusteddevice.repository.RegisterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object RegisterModule {

    @Provides
    fun provideService(retrofit: Retrofit): RegisterService {
        return retrofit.create(RegisterService::class.java)
    }

    @Provides
    fun provideRepository(service: RegisterService, preferences: PreferencesStorage): RegisterRepository {
        return RegisterRepositoryImpl(service, preferences)
    }
}
