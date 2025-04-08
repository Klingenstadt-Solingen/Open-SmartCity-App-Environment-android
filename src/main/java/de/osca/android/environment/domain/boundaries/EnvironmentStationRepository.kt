package de.osca.android.environment.domain.boundaries

import com.google.android.gms.maps.model.LatLngBounds
import de.osca.android.environment.domain.entity.EnvironmentStation

interface EnvironmentStationRepository {
    suspend fun getStationsBySubCategoryId(subCategoryId: String, limit: Int, skip: Int): List<EnvironmentStation>
    suspend fun getStationById(stationId: String): EnvironmentStation
    suspend fun getStationsWithinGeoBox(subCategoryId: String, bounds: LatLngBounds, skip: Int, limit: Int): List<EnvironmentStation>
    suspend fun getAllStationsWithinGeoBox(bounds: LatLngBounds, skip: Int, limit: Int): List<EnvironmentStation>
}
