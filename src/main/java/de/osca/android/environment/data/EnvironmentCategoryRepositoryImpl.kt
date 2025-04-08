package de.osca.android.environment.data

import com.parse.ParseQuery
import com.parse.coroutines.suspendFind
import com.parse.ktx.orderByAscending
import de.osca.android.environment.di.envCacheTime
import de.osca.android.environment.domain.boundaries.EnvironmentCategoryRepository
import de.osca.android.environment.domain.entity.EnvironmentCategory import javax.inject.Inject

class EnvironmentCategoryRepositoryImpl @Inject constructor(
    private val parseRequestHandler: ParseRequestHandler
) : EnvironmentCategoryRepository {

    override suspend fun getCategories(): List<EnvironmentCategory> {
        val query = ParseQuery.getQuery(EnvironmentCategory::class.java)
            .orderByAscending(EnvironmentCategory::order)
            .setMaxCacheAge(envCacheTime)
            .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)

        return parseRequestHandler.executeQuery(query::suspendFind) ?: emptyList()
    }
}
