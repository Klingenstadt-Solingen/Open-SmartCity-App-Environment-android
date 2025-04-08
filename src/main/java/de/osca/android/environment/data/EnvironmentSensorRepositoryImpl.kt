package de.osca.android.environment.data

import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.coroutines.callCloudFunction
import com.parse.coroutines.getById
import com.parse.coroutines.suspendFind
import com.parse.ktx.include
import com.parse.ktx.whereEqualTo
import com.parse.ktx.whereMatchesQuery
import de.osca.android.environment.di.envCacheTime
import de.osca.android.environment.domain.boundaries.EnvironmentSensorRepository
import de.osca.android.environment.domain.entity.EnvironmentSensor
import de.osca.android.environment.domain.entity.EnvironmentSensorValue
import de.osca.android.environment.domain.entity.EnvironmentSubCategory
import javax.inject.Inject

class EnvironmentSensorRepositoryImpl
    @Inject
    constructor(
        private val parseRequestHandler: ParseRequestHandler,
    ) : EnvironmentSensorRepository {
        override suspend fun getSensorById(sensorId: String): EnvironmentSensor {
            val query =
                ParseQuery.getQuery(EnvironmentSensor::class.java).include("station.poi")
                    .include(EnvironmentSensor::sensorType)
                    .include("sensorType.icon")
                    .setMaxCacheAge(envCacheTime)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)

            return parseRequestHandler.executeQuery { query.getById(sensorId) } ?: EnvironmentSensor()
        }

        override suspend fun getSensorsByTheirId(sensorId: List<String>): List<EnvironmentSensor> {
            val query =
                ParseQuery.getQuery(EnvironmentSensor::class.java).include("station.poi")
                    .include(EnvironmentSensor::sensorType)
                    .include("sensorType.icon")
                    .whereContainedIn("objectId", sensorId)
                    .setMaxCacheAge(envCacheTime)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)

            return parseRequestHandler.executeQuery { query.suspendFind() }
                ?: emptyList<EnvironmentSensor>()
        }

        override suspend fun getAllSensorsByStationId(stationId: String): List<EnvironmentSensor> {
            val subCategoryTypeQuery =
                ParseQuery.getQuery(EnvironmentSensor::class.java)
                    .whereEqualTo(EnvironmentSensor::station, stationId)
                    .include(EnvironmentSensor::sensorType)
                    .include("sensorType.icon")
                    .include(EnvironmentSensor::station)
                    .setMaxCacheAge(envCacheTime)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)

            return parseRequestHandler.executeQuery(subCategoryTypeQuery::suspendFind) ?: emptyList()
        }

        override suspend fun getSensorBySubCategoryId(
            stationId: String,
            subCategoryId: String,
        ): List<EnvironmentSensor> {
            val sensorTypeQuery =
                ParseObject.createWithoutData(
                    EnvironmentSubCategory::class.java,
                    subCategoryId,
                ).sensorTypes.query
            val subCategoryTypeQuery =
                ParseQuery.getQuery(EnvironmentSensor::class.java)
                    .whereEqualTo(EnvironmentSensor::station, stationId)
                    .whereMatchesQuery(EnvironmentSensor::sensorType, sensorTypeQuery)
                    .include(EnvironmentSensor::sensorType)
                    .include("sensorType.icon")
                    .include(EnvironmentSensor::station)
                    .setMaxCacheAge(envCacheTime)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)

            return parseRequestHandler.executeQuery(subCategoryTypeQuery::suspendFind) ?: emptyList()
        }

        override suspend fun getSenorValuesByRefId(refId: String): List<EnvironmentSensorValue> {
            val params =
                mapOf(Pair("refId", refId), Pair("limit", 60), Pair("skip", 0)) // uses refId variable

            return callCloudFunction<List<EnvironmentSensorValue>>(
                "environmentSensorHistory",
                params,
            ).reversed()
        }
    }
