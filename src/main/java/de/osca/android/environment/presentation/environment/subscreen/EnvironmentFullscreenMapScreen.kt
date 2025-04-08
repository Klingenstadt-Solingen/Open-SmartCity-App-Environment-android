package de.osca.android.environment.presentation.environment.subscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.osca.android.environment.R
import de.osca.android.environment.navigation.EnvironmentNavItems
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.environment.presentation.components.EnvironmentMap
import de.osca.android.environment.presentation.components.envNavigateTo
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentStationMapViewModel
import de.osca.android.essentials.presentation.component.design.LocalMasterDesignArgs
import de.osca.android.essentials.presentation.component.screen_wrapper.ScreenWrapper

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EnvironmentFullScreenMapScreen(
    envNavController: NavController,
    showBottomSheet: MutableState<Boolean>?,
    stationMapViewModel: EnvironmentStationMapViewModel = hiltViewModel(),
) {
    val design = LocalEnvironmentDesignArgs.current
    val masterDesignArgs = LocalMasterDesignArgs.current
    LaunchedEffect(Unit) {
        stationMapViewModel.initFetch()
    }
    Scaffold(
        topBar = { },
        floatingActionButton = {
            Surface(
                modifier =
                    Modifier.clickable {
                        showBottomSheet?.value = false
                    },
                shape = RoundedCornerShape(10.dp),
                color = Color.White,
                shadowElevation = design.envTabBackgroundShadowElevation,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_map_with_marker),
                    contentDescription = null,
                    modifier = Modifier.size(55.dp).padding(10.dp),
                    tint = Color(0, 67, 115),
                )
            }
        },
    ) {
        ScreenWrapper(
            masterDesignArgs,
            design,
            withTopBar = false,
            screenWrapperState = stationMapViewModel.wrapperState,
        ) {
            Card(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(
                            design.mRootBoarderSpacing ?: masterDesignArgs.mBorderSpace,
                        ), // TODO: replace with design variable in design module
            ) {
                fun markerOnClick(stationId: String): Boolean {
                    envNavigateTo(
                        envNavController,
                        EnvironmentNavItems.EnvironmentNavItemSensorGrid,
                        "objectId" to stationId,
                        "backButtonTitle" to "Alle",
                        "subCategoryId" to "all",
                    )
                    showBottomSheet?.value = false
                    return true
                }

                EnvironmentMap(
                    mapStations = stationMapViewModel.stations.toTypedArray(),
                    markerOnClick = { stationId -> markerOnClick(stationId) },
                ) { bounds ->
                    stationMapViewModel.killChildren()
                    stationMapViewModel.fetchAllStationsWithinGeoBox(bounds = bounds)
                }
            }
        }
    }
}
