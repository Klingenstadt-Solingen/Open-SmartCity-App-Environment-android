package de.osca.android.environment.domain.boundaries

import kotlinx.coroutines.flow.Flow

interface EnvironmentStorage {

    fun loadActivatedSensors(): Flow<List<String>>
    suspend fun saveActivatedSensors(list: List<String>)
}