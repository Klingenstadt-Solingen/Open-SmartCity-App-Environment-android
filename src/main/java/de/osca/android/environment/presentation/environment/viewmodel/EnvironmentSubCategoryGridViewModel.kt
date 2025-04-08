package de.osca.android.environment.presentation.environment.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.environment.di.envLoadAtOnce
import de.osca.android.environment.domain.boundaries.EnvironmentSubCategoryRepository
import de.osca.android.environment.domain.entity.EnvironmentSubCategory
import de.osca.android.essentials.presentation.base.BaseViewModel
import de.osca.android.essentials.utils.extensions.displayContent
import de.osca.android.essentials.utils.extensions.loading
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import javax.inject.Inject

@HiltViewModel
class EnvironmentSubCategoryGridViewModel @Inject constructor(
    private val subCategoryRepository: EnvironmentSubCategoryRepository,
) : BaseViewModel() {
    val subCategories = mutableStateListOf<EnvironmentSubCategory>()

    fun initFetch(categoryId: String ): Job = launchDataLoad {
        wrapperState.loading()
        killChildren()
        subCategories.clear()
        fetchSensorSubCategories(categoryId)
    }

    fun killChildren() {
        viewModelScope.coroutineContext.cancelChildren()
    }

    fun fetchSensorSubCategories(categoryId: String , limit: Int = envLoadAtOnce, skip: Int = 0): Job = launchDataLoad {
        val newSubCategories = subCategoryRepository.getSubCategoriesByCategoryId(categoryId, limit, skip)
        subCategories.addAll(newSubCategories)
        wrapperState.displayContent()
    }
}
