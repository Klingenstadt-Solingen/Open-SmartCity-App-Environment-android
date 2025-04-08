package de.osca.android.environment.data

import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.coroutines.first
import com.parse.coroutines.suspendFind
import de.osca.android.environment.di.envLocaleCacheTime
import de.osca.android.environment.domain.EnvironmentLocaleRepository
import de.osca.android.environment.domain.entity.EnvironmentLocale
import javax.inject.Inject

class EnvironmentLocaleRepositoryImpl
    @Inject
    constructor(
        private val parseRequestHandler: ParseRequestHandler,
    ) : EnvironmentLocaleRepository {
        override suspend fun getLocales(language: String): List<EnvironmentLocale> {
            val query =
                ParseQuery.getQuery(EnvironmentLocale::class.java)
                    .whereEqualTo("locale", language)
                    .setMaxCacheAge(envLocaleCacheTime)
                    .setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE)
                    .setLimit(10_000)

            return parseRequestHandler.executeQuery(query::suspendFind) ?: emptyList()
        }

        override suspend fun getLocaleByKey(
            key: String,
            language: String,
        ): EnvironmentLocale? {
            val query =
                ParseQuery.getQuery(EnvironmentLocale::class.java)
                    .whereEqualTo("key", key)
                    .whereEqualTo("locale", language)
                    .setMaxCacheAge(envLocaleCacheTime)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
            return try {
                parseRequestHandler.executeQuery(query::first)
            } catch (e: ParseException) {
                if (e.code == ParseException.OBJECT_NOT_FOUND) {
                    null
                } else {
                    throw e
                }
            }
        }
    }
