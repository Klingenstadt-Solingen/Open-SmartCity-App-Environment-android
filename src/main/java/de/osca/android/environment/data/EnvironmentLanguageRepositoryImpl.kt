package de.osca.android.environment.data

import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.coroutines.first
import de.osca.android.environment.di.envLocaleCacheTime
import de.osca.android.environment.domain.EnvironmentLanguageRepository
import de.osca.android.environment.domain.entity.EnvironmentLanguage
import javax.inject.Inject

class EnvironmentLanguageRepositoryImpl
    @Inject
    constructor(
        private val parseRequestHandler: ParseRequestHandler,
    ) : EnvironmentLanguageRepository {
        override suspend fun getAlternativeLanguage(prefix: String): String? {
            val query =
                ParseQuery.getQuery(EnvironmentLanguage::class.java)
                    .whereStartsWith("locale", prefix.split("-").first())
                    .orderByAscending("priority")

            return try {
                parseRequestHandler.executeQuery(query::first)?.locale
            } catch (e: ParseException) {
                if (e.code == ParseException.OBJECT_NOT_FOUND) {
                    null
                } else {
                    throw e
                }
            }
        }

        override suspend fun checkLanguage(language: String): Boolean {
            val query =
                ParseQuery.getQuery(EnvironmentLanguage::class.java)
                    .whereEqualTo("locale", language)
                    .setMaxCacheAge(envLocaleCacheTime)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)

            return try {
                (parseRequestHandler.executeQuery(query::count) ?: 0) > 0
            } catch (e: ParseException) {
                if (e.code == ParseException.OBJECT_NOT_FOUND) {
                    false
                } else {
                    throw e
                }
            }
        }
    }
