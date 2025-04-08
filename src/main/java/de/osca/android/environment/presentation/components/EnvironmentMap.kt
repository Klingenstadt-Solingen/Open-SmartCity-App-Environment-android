package de.osca.android.environment.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import de.osca.android.environment.domain.entity.EnvironmentStation
import de.osca.android.environment.presentation.args.TestTags

/**
 * Draws a map
 * @param markerOptions marker options for markers
 * @param geoPointList coordinates for the markers
 */

@Composable
fun EnvironmentMap(
    vararg mapStations: EnvironmentStation? = emptyArray(),
    initLatLng: LatLng = LatLng(51.170975, 7.083238),
    initZoom: Float = 13f,
    markerOnClick: (String) -> Boolean = {false},
    content: (LatLngBounds) -> Unit = {}
) {
    var updateMarker = false
    val cameraPosition = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(
        if (mapStations.isNotEmpty()) mapStations.first()?.poi?.geopoint ?: initLatLng else initLatLng,
        initZoom) }
    val uiSettings = remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = false))
    }
    GoogleMap(modifier = Modifier.fillMaxSize().testTag(TestTags.GOOGLE_MAP),
        cameraPositionState = cameraPosition,
        onMapLoaded = {
            cameraPosition.projection?.visibleRegion?.latLngBounds?.let {
                content(it)
            }
        },
        uiSettings = uiSettings.value
    ) {

        mapStations.forEach { item ->
            if (item?.poi?.has("geopoint") == true) {
                Marker(state = MarkerState(position = item.poi?.geopoint!!),
                    onClick = { marker -> markerOnClick(item.objectId) }, tag = item.name, title = item.name)
            }
        }
        if (cameraPosition.isMoving) updateMarker = true
        if (!cameraPosition.isMoving && updateMarker) {
            updateMarker = false
            cameraPosition.projection?.visibleRegion?.latLngBounds?.let {
                content(it)
            }
        }
    }
}
