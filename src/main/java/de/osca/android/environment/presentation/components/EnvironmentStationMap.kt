package de.osca.android.environment.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentStationMapViewModel
import de.osca.android.essentials.domain.entity.navigation.NavigationItem
import de.osca.android.essentials.presentation.component.design.LocalMasterDesignArgs
import de.osca.android.essentials.presentation.component.screen_wrapper.ScreenWrapper

@Composable
fun EnvironmentStationMap(
    envNavController: NavController,
    navigationItem: NavigationItem,
    subCategoryId: String,
    backButtonTitle: String,
    stationMapViewModel: EnvironmentStationMapViewModel = hiltViewModel()
) {
    val design = LocalEnvironmentDesignArgs.current
    val masterDesignArgs = LocalMasterDesignArgs.current
    LaunchedEffect(subCategoryId) {stationMapViewModel.initFetch()}
    ScreenWrapper(masterDesignArgs, design, withTopBar = false, screenWrapperState =  stationMapViewModel.wrapperState) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 5.dp,
                    bottom = 10.dp
                ) // TODO: replace with design variable in design module
        )
        {
            fun markerOnClick(stationId: String): Boolean {
                envNavigateTo(
                    envNavController,
                    navigationItem,
                    "objectId" to stationId,
                    "subCategoryId" to subCategoryId,
                    "backButtonTitle" to backButtonTitle
                )
                return true
            }

            EnvironmentMap(
                mapStations = stationMapViewModel.stations.toTypedArray(),
                markerOnClick = { stationId -> markerOnClick(stationId) }
            ) { bounds ->
                stationMapViewModel.killChildren()
                stationMapViewModel.fetchStationsByBoundry(subCategoryId, bounds)
            }
        }
    }
}
