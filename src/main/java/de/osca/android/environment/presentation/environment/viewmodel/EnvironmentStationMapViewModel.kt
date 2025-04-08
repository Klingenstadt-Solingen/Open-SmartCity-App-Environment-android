package de.osca.android.environment.presentation.environment.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.environment.di.envLoadAtOnce
import de.osca.android.environment.domain.boundaries.EnvironmentStationRepository
import de.osca.android.environment.domain.entity.EnvironmentStation
import de.osca.android.essentials.presentation.base.BaseViewModel
import de.osca.android.essentials.utils.extensions.displayContent
import de.osca.android.essentials.utils.extensions.loading
import de.osca.android.essentials.utils.extensions.resetWith
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import javax.inject.Inject

@HiltViewModel
class EnvironmentStationMapViewModel @Inject constructor(
    private val stationRepository: EnvironmentStationRepository
) : BaseViewModel() {
    val stations = mutableStateListOf<EnvironmentStation>()

    fun initFetch() {
        wrapperState.loading()
        killChildren()
        stations.clear()
        wrapperState.displayContent()
    }

    fun killChildren() {
        viewModelScope.coroutineContext.cancelChildren()
    }

    fun fetchStationsByBoundry(subCategoryId: String, bounds: LatLngBounds, limit: Int = envLoadAtOnce): Job = launchDataLoad {
       stations.clear()
        var count = 100
        var skip = 0
        val newpois = mutableListOf<EnvironmentStation>()
        while (count > 0) {
            newpois.addAll(stationRepository.getStationsWithinGeoBox(subCategoryId, bounds, skip, limit))
            skip+=limit
            count-=limit
            stations.resetWith(newpois)
        }
    }

    fun fetchAllStationsWithinGeoBox(bounds: LatLngBounds, limit: Int = envLoadAtOnce): Job = launchDataLoad {
        stations.clear()
        var count = 100
        var skip = 0
        val newpois = mutableListOf<EnvironmentStation>()
        while (count > 0) {
            newpois.addAll(stationRepository.getAllStationsWithinGeoBox(bounds, skip, limit))
            skip+=limit
            count-=limit
            stations.resetWith(newpois)
        }
    }
}
