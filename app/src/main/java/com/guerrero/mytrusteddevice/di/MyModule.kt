package com.guerrero.mytrusteddevice.di

import com.guerrero.mytrusteddevice.model.repository.MyRepository
import com.guerrero.mytrusteddevice.model.repository.MyRepositoryImpl
import com.guerrero.mytrusteddevice.model.web.MyService
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
    fun provideRepository(service: MyService): MyRepository {
        return MyRepositoryImpl(service)
    }
}
