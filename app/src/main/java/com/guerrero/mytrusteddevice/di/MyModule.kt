package com.guerrero.mytrusteddevice.di

import com.guerrero.mytrusteddevice.network.MyService
import com.guerrero.mytrusteddevice.preferences.PreferencesStorage
import com.guerrero.mytrusteddevice.repository.MyRepository
import com.guerrero.mytrusteddevice.repository.MyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object MyModule {

    @Provides
    fun provideMyService(retrofit: Retrofit): MyService {
        return retrofit.create(MyService::class.java)
    }

    @Provides
    fun provideRepository(service: MyService, preferences: PreferencesStorage): MyRepository {
        return MyRepositoryImpl(service, preferences)
    }
}
