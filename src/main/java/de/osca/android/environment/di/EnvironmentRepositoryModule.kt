package de.osca.android.environment.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.osca.android.environment.data.*
import de.osca.android.environment.domain.EnvironmentLanguageRepository
import de.osca.android.environment.domain.EnvironmentLocaleRepository
import de.osca.android.environment.domain.boundaries.*

@Module
@InstallIn(SingletonComponent::class)
abstract class EnvironmentRepositoryModule {
    @Binds
    abstract fun provideSensorCategoryRepository(repositoryImpl: EnvironmentCategoryRepositoryImpl): EnvironmentCategoryRepository
    @Binds
    abstract fun provideSensorSubCategoryRepository(repositoryImpl: EnvironmentSubCategoryRepositoryImpl): EnvironmentSubCategoryRepository
    @Binds
    abstract fun provideSensorStationRepository(repositoryImpl: EnvironmentStationRepositoryImpl): EnvironmentStationRepository
    @Binds
    abstract fun provideSensorValueRepository(repositoryImpl: EnvironmentSensorRepositoryImpl): EnvironmentSensorRepository
    @Binds
    abstract fun provideLocaleRepository(repositoryImpl: EnvironmentLocaleRepositoryImpl): EnvironmentLocaleRepository
    @Binds
    abstract fun provideLanguageRepository(repositoryImpl: EnvironmentLanguageRepositoryImpl): EnvironmentLanguageRepository
}
