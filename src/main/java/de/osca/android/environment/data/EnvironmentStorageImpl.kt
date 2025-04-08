package de.osca.android.environment.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import de.osca.android.environment.domain.boundaries.EnvironmentStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class EnvironmentStorageImpl
    @Inject
    constructor(
        @ApplicationContext val context: Context,
    ) :
    EnvironmentStorage {
        override fun loadActivatedSensors(): Flow<List<String>> {
            return try {
                context.storage.data
                    .map { preferences ->
                        preferences[sensor_favorite_list]?.let {
                            Json.decodeFromString<List<String>>(it)
                        } ?: emptyList()
                    }
            } catch (ex: Exception) {
                emptyFlow<List<String>>()
            }
        }

        override suspend fun saveActivatedSensors(list: List<String>) {
            context.storage.edit { preferences ->
                preferences[sensor_favorite_list] = Json.encodeToString(list)
            }
        }

        companion object {
            const val ENVIRONMENT_PREFERENCES_NAME = "environment_preferences"
            val sensor_favorite_list = stringPreferencesKey("environment_sensor_favorite_list")

            private val Context.storage: DataStore<Preferences> by preferencesDataStore(
                ENVIRONMENT_PREFERENCES_NAME,
            )
        }
    }

