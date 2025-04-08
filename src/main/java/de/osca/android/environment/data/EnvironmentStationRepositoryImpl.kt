package de.osca.android.environment.data

import com.google.android.gms.maps.model.LatLngBounds
import com.parse.ParseGeoPoint
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.coroutines.getById
import com.parse.coroutines.suspendFind
import com.parse.ktx.whereMatchesQuery
import com.parse.ktx.whereWithinGeoBox
import de.osca.android.environment.di.envCacheTime
import de.osca.android.environment.domain.boundaries.EnvironmentStationRepository
import de.osca.android.environment.domain.entity.EnvironmentSensor
import de.osca.android.environment.domain.entity.EnvironmentStation
import de.osca.android.environment.domain.entity.EnvironmentSubCategory
import de.osca.android.essentials.domain.entity.ParsePOI
import javax.inject.Inject

class EnvironmentStationRepositoryImpl
    @Inject
    constructor(
        private val parseRequestHandler: ParseRequestHandler,
    ) : EnvironmentStationRepository {
        override suspend fun getStationById(stationId: String): EnvironmentStation {
            val query =
                ParseQuery.getQuery(EnvironmentStation::class.java)
                    .setMaxCacheAge(envCacheTime)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)

            return parseRequestHandler.executeQuery { query.getById(stationId) } ?: EnvironmentStation()
        }

        override suspend fun getStationsBySubCategoryId(
            subCategoryId: String,
            limit: Int,
            skip: Int,
        ): List<EnvironmentStation> {
            val sensorTypeQuery =
                ParseObject.createWithoutData(
                    EnvironmentSubCategory::class.java,
                    subCategoryId,
                ).sensorTypes.query
            val sensorQuery =
                ParseQuery.getQuery(EnvironmentSensor::class.java)
                    .whereMatchesQuery(EnvironmentSensor::sensorType, sensorTypeQuery)
            val query =
                ParseQuery.getQuery(EnvironmentStation::class.java)
                    .whereMatchesKeyInQuery(
                        ParseObject.KEY_OBJECT_ID,
                        EnvironmentSensor::station.name,
                        sensorQuery,
                    )
                    .setMaxCacheAge(envCacheTime)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
                    .setLimit(limit)
                    .setSkip(skip)

            return parseRequestHandler.executeQuery(query::suspendFind) ?: emptyList()
        }

        override suspend fun getStationsWithinGeoBox(
            subCategoryId: String,
            bounds: LatLngBounds,
            skip: Int,
            limit: Int,
        ): List<EnvironmentStation> {
            val sensorTypeQuery =
                ParseObject.createWithoutData(
                    EnvironmentSubCategory::class.java,
                    subCategoryId,
                ).sensorTypes.query
            val sensorQuery =
                ParseQuery.getQuery(EnvironmentSensor::class.java)
                    .whereMatchesQuery(EnvironmentSensor::sensorType, sensorTypeQuery)

            val swPoint = ParseGeoPoint(bounds.southwest.latitude, bounds.southwest.longitude)
            val nePoint = ParseGeoPoint(bounds.northeast.latitude, bounds.northeast.longitude)

            val poiQuery =
                ParseQuery.getQuery(ParsePOI::class.java)
                    .whereWithinGeoBox(ParsePOI::geopoint, swPoint, nePoint)
                    .setMaxCacheAge(envCacheTime)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
                    .setLimit(limit)
                    .setSkip(skip)

            val query =
                ParseQuery.getQuery(EnvironmentStation::class.java)
                    .whereMatchesKeyInQuery(
                        ParseObject.KEY_OBJECT_ID,
                        EnvironmentSensor::station.name,
                        sensorQuery,
                    )
                    .whereMatchesQuery("poi", poiQuery)
                    .selectKeys(setOf("objectId", "name", "poi", "poi.geopoint"))

            return parseRequestHandler.executeQuery(query::suspendFind) ?: emptyList()
        }

        override suspend fun getAllStationsWithinGeoBox(
            bounds: LatLngBounds,
            skip: Int,
            limit: Int,
        ): List<EnvironmentStation> {
            val swPoint = ParseGeoPoint(bounds.southwest.latitude, bounds.southwest.longitude)
            val nePoint = ParseGeoPoint(bounds.northeast.latitude, bounds.northeast.longitude)

            val poiQuery =
                ParseQuery.getQuery(ParsePOI::class.java)
                    .whereWithinGeoBox(ParsePOI::geopoint, swPoint, nePoint)
                    .setMaxCacheAge(envCacheTime)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
                    .setLimit(limit)
                    .setSkip(skip)

            val query =
                ParseQuery.getQuery(EnvironmentStation::class.java)
                    .whereMatchesQuery("poi", poiQuery)
                    .selectKeys(setOf("objectId", "name", "poi", "poi.geopoint"))
                    .setMaxCacheAge(envCacheTime)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
                    .setLimit(limit)
                    .setSkip(skip)

            return parseRequestHandler.executeQuery(query::suspendFind) ?: emptyList()
        }
    }
