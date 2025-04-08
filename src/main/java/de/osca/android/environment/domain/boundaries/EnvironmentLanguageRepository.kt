package de.osca.android.environment.domain

interface EnvironmentLanguageRepository {
    suspend fun getAlternativeLanguage(prefix: String): String?

    suspend fun checkLanguage(language: String): Boolean
}
