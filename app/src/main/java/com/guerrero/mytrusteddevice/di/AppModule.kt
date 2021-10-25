package com.guerrero.mytrusteddevice.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

private const val BASE_URL = "http://10.0.2.2:5000/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
}