package de.osca.android.environment.presentation.environment.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.environment.di.envLoadAtOnce
import de.osca.android.environment.domain.boundaries.EnvironmentStationRepository
import de.osca.android.environment.domain.entity.EnvironmentStation
import de.osca.android.essentials.presentation.base.BaseViewModel
import de.osca.android.essentials.utils.extensions.displayContent
import de.osca.android.essentials.utils.extensions.loading
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import javax.inject.Inject

@HiltViewModel
class EnvironmentStationListViewModel @Inject constructor(
    private val stationRepository: EnvironmentStationRepository
) : BaseViewModel() {
    val stations = mutableStateListOf<EnvironmentStation>()

    fun initFetch(subCategoryId: String) {
        wrapperState.loading()
        killChildren()
        stations.clear()
        fetchStations(subCategoryId)
    }

    fun killChildren() {
        viewModelScope.coroutineContext.cancelChildren()
    }

    fun fetchStations(subCategoryId: String, limit: Int = envLoadAtOnce, skip: Int = 0): Job = launchDataLoad {
        val newSensorStations = stationRepository.getStationsBySubCategoryId(subCategoryId, limit, skip)
        stations.addAll(newSensorStations)
        wrapperState.displayContent()
    }
}
