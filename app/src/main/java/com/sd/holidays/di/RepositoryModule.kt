package com.sd.holidays.di

import com.sd.holidays.domain.Repository
import com.sd.holidays.data.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindsRepository(impl: RepositoryImpl): Repository
}