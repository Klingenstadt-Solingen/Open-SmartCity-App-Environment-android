package de.osca.android.environment.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.osca.android.environment.data.EnvironmentParseRegistrationImpl
import de.osca.android.environment.data.ParseRequestHandler
import de.osca.android.environment.data.EnvironmentStorageImpl
import de.osca.android.environment.domain.boundaries.EnvironmentParseRegistration
import de.osca.android.environment.domain.boundaries.EnvironmentStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EnvironmentModule {
    @Singleton
    @Provides
    fun provideParseRequestHandler(): ParseRequestHandler = ParseRequestHandler()

    @Singleton
    @Provides
    fun provideEnvironmentParseRegistration(): EnvironmentParseRegistration = EnvironmentParseRegistrationImpl()

    @Singleton
    @Provides
    fun providesWasteStorage(@ApplicationContext context: Context): EnvironmentStorage {
        return EnvironmentStorageImpl(context)
    }
}

const val envLoadAtOnce = 20
const val envCacheTime: Long = 600000 // milliseconds
const val envLocaleCacheTime: Long = 28800000 // milliseconds
