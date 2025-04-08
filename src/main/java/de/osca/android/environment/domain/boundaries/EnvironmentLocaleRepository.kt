package de.osca.android.environment.domain

import de.osca.android.environment.domain.entity.EnvironmentLocale

interface EnvironmentLocaleRepository {
    suspend fun getLocales(language: String): List<EnvironmentLocale>

    suspend fun getLocaleByKey(
        key: String,
        language: String,
    ): EnvironmentLocale?
}
