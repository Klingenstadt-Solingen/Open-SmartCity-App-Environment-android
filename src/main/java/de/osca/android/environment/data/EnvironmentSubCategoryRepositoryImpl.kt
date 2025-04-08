package de.osca.android.environment.data

import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.coroutines.getById
import com.parse.coroutines.suspendFind
import com.parse.ktx.include
import com.parse.ktx.orderByAscending
import de.osca.android.environment.di.envCacheTime
import de.osca.android.environment.domain.boundaries.EnvironmentSubCategoryRepository
import de.osca.android.environment.domain.entity.EnvironmentCategory
import de.osca.android.environment.domain.entity.EnvironmentSubCategory
import javax.inject.Inject

class EnvironmentSubCategoryRepositoryImpl @Inject constructor(
    private val parseRequestHandler: ParseRequestHandler
) : EnvironmentSubCategoryRepository {

    override suspend fun getSubCategoryById(subCategoryId: String): EnvironmentSubCategory {
        val query = ParseQuery.getQuery(EnvironmentSubCategory::class.java)
            .include(EnvironmentSubCategory::icon)
            .setMaxCacheAge(envCacheTime)
            .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)

        return parseRequestHandler.executeQuery { query.getById(subCategoryId) } ?: EnvironmentSubCategory()
    }
    override suspend fun getSubCategoriesByCategoryId(categoryId: String, limit: Int, skip: Int): List<EnvironmentSubCategory> {
        val query = ParseObject.createWithoutData(EnvironmentCategory::class.java, categoryId)
            .subCategories.query
            .orderByAscending(EnvironmentSubCategory::order)
            .include(EnvironmentSubCategory::icon)
            .setMaxCacheAge(envCacheTime)
            .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
            .setLimit(limit)
            .setSkip(skip)

        return parseRequestHandler.executeQuery(query::suspendFind) ?: emptyList()
    }
}
