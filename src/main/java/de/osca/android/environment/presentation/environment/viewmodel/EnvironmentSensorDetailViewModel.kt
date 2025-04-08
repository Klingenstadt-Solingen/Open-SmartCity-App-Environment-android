package de.osca.android.environment.presentation.environment.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.environment.domain.boundaries.EnvironmentSensorRepository
import de.osca.android.environment.domain.entity.EnvironmentGraphStatus
import de.osca.android.environment.domain.entity.EnvironmentSensor
import de.osca.android.essentials.presentation.base.BaseViewModel
import de.osca.android.essentials.utils.extensions.displayContent
import de.osca.android.essentials.utils.extensions.loading
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import javax.inject.Inject

@HiltViewModel
class EnvironmentSensorDetailViewModel @Inject constructor(
    private val sensorRepository: EnvironmentSensorRepository
) : BaseViewModel() {
    val sensor = mutableStateOf<EnvironmentSensor?>(null)
    var sensorDataStatus = mutableStateOf<EnvironmentGraphStatus>(EnvironmentGraphStatus.Loading)

    fun initFetch(sensorValueId: String): Job = launchDataLoad {
        wrapperState.loading()
        sensorDataStatus.value = EnvironmentGraphStatus.Loading
        killChildren()
        fetchSensor(sensorValueId)
    }

    fun killChildren() {
        viewModelScope.coroutineContext.cancelChildren()
    }

    private fun fetchSensor(sensorValueId: String): Job = launchDataLoad {
        sensor.value = sensorRepository.getSensorById(sensorValueId)
        wrapperState.displayContent()
    }

    fun fetchGraphData(refId: String): Job = launchDataLoad(
        onFailure = {exception ->
            sensorDataStatus.value = EnvironmentGraphStatus.Error(errorHandler.resolveMessage(exception))
        }) {
        val data = sensorRepository.getSenorValuesByRefId(refId)
        if (data.isNotEmpty()) {
            sensorDataStatus.value = EnvironmentGraphStatus.Loaded(data)
        } else {
            sensorDataStatus.value = EnvironmentGraphStatus.Error("no_data")
        }
    }
}
