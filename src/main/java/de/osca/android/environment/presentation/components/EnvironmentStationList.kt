package de.osca.android.environment.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentStationListViewModel
import de.osca.android.essentials.domain.entity.navigation.NavigationItem
import de.osca.android.essentials.presentation.component.design.LocalMasterDesignArgs
import de.osca.android.essentials.presentation.component.screen_wrapper.ScreenWrapper

@Composable
fun EnvironmentStationList(
    envNavController: NavController,
    navigationItem: NavigationItem,
    subCategoryId: String,
    backButtonTitle: String,
    stationListViewModel: EnvironmentStationListViewModel = hiltViewModel()

) {
    val design = LocalEnvironmentDesignArgs.current
    val masterDesignArgs = LocalMasterDesignArgs.current
    LaunchedEffect(subCategoryId) {stationListViewModel.initFetch(subCategoryId)}
    ScreenWrapper(masterDesignArgs, design, withTopBar = false, screenWrapperState =  stationListViewModel.wrapperState) {
        val listState = rememberLazyListState()
        val itemList = stationListViewModel.stations
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            itemsIndexed(itemList) { index, item ->
                if (itemList.lastIndex == index) {
                    LaunchedEffect(index) {
                        stationListViewModel.fetchStations(
                            subCategoryId,
                            skip = index + 1
                        )
                    }
                }
                Button(
                    onClick = {
                        envNavigateTo(
                            envNavController,
                            navigationItem,
                            "objectId" to item.objectId,
                            "subCategoryId" to subCategoryId,
                            "backButtonTitle" to backButtonTitle
                        )
                    },
                    colors = buttonColors(backgroundColor = design.envMeasurementLocationButtonColor),
                    elevation = ButtonDefaults.elevation(design.envMeasurementLocationButtonElevation)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(design.envMeasurementLocationRowSpacing)
                    ) {
                        Icon(
                            modifier = Modifier.size(design.envMeasurementLocationIconSize),
                            painter = painterResource(design.envMeasurementLocationIcon),
                            contentDescription = "",
                            tint = design.envMeasurementLocationIconColor
                        )
                        Text(
                            text = item.name,
                            fontSize = design.envMeasurementLocationTextSize,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
