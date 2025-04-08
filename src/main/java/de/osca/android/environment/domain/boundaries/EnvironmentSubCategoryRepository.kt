package de.osca.android.environment.domain.boundaries

import de.osca.android.environment.domain.entity.EnvironmentSubCategory

interface EnvironmentSubCategoryRepository {
    suspend fun getSubCategoriesByCategoryId(categoryId: String, limit: Int, skip: Int): List<EnvironmentSubCategory>
    suspend fun getSubCategoryById(subCategoryId: String): EnvironmentSubCategory
}
