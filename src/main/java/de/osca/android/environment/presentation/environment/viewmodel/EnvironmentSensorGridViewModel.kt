package de.osca.android.environment.presentation.environment.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.environment.domain.boundaries.EnvironmentSensorRepository
import de.osca.android.environment.domain.entity.EnvironmentSensor
import de.osca.android.essentials.presentation.base.BaseViewModel
import de.osca.android.essentials.utils.extensions.displayContent
import de.osca.android.essentials.utils.extensions.loading
import de.osca.android.essentials.utils.extensions.resetWith
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import javax.inject.Inject

@HiltViewModel
class EnvironmentSensorGridViewModel @Inject constructor(
    private val sensorRepository: EnvironmentSensorRepository
) : BaseViewModel() {
    val sensors = mutableStateListOf<EnvironmentSensor>()
    fun initFetch(locationId: String, subCategoryId: String) {
        wrapperState.loading()
        killChildren()
        sensors.clear()
        fetchSensors(locationId, subCategoryId)
    }
    fun killChildren() {
        viewModelScope.coroutineContext.cancelChildren()
    }

    fun fetchSensors(locationId: String, subCategoryId: String): Job = launchDataLoad {
        if(subCategoryId == "all") sensors.resetWith(sensorRepository.getAllSensorsByStationId(locationId))
        else sensors.resetWith(sensorRepository.getSensorBySubCategoryId(locationId, subCategoryId))
        sensors.sortBy { it.sensorType.order }
        wrapperState.displayContent()
    }
}
