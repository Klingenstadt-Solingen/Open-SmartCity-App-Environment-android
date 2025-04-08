package de.osca.android.environment.domain.boundaries

import de.osca.android.environment.domain.entity.EnvironmentCategory

interface EnvironmentCategoryRepository {
    suspend fun getCategories(): List<EnvironmentCategory>
}
