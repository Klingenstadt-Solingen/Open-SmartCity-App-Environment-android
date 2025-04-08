package de.osca.android.environment.domain.boundaries

import de.osca.android.environment.domain.entity.EnvironmentSensor
import de.osca.android.environment.domain.entity.EnvironmentSensorValue

interface EnvironmentSensorRepository {
    suspend fun getSensorById(sensorId: String): EnvironmentSensor
    suspend fun getSensorsByTheirId(sensorId: List<String>): List<EnvironmentSensor>
    suspend fun getAllSensorsByStationId(stationId: String): List<EnvironmentSensor>
    suspend fun getSensorBySubCategoryId(stationId: String, subCategoryId: String): List<EnvironmentSensor>
    suspend fun getSenorValuesByRefId(refId: String): List<EnvironmentSensorValue>
}
