package de.osca.android.environment.presentation.environment.subscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.osca.android.environment.navigation.EnvironmentNavItems
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.environment.presentation.components.BackButton
import de.osca.android.environment.presentation.components.EnvironmentSubtitle
import de.osca.android.environment.presentation.components.card.EnvironmentSensorStationGridCard
import de.osca.android.environment.presentation.components.envNavigateTo
import de.osca.android.environment.presentation.components.grid.EnvironmentGrid
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentSensorGridViewModel
import de.osca.android.environment.presentation.environment.viewmodel.locale
import de.osca.android.essentials.presentation.component.design.LocalMasterDesignArgs
import de.osca.android.essentials.presentation.component.screen_wrapper.ScreenWrapper

@Composable
fun EnvironmentSensorGridScreen(
    envNavController: NavController,
    stationId: String,
    subCategoryId: String,
    backButtonTitle: String,
    sensorViewModel: EnvironmentSensorGridViewModel = hiltViewModel(),
) {
    val design = LocalEnvironmentDesignArgs.current
    val masterDesignArgs = LocalMasterDesignArgs.current
    Column(
        modifier = Modifier.padding(
            PaddingValues(
                horizontal = design.mRootBoarderSpacing ?: masterDesignArgs.mBorderSpace
            )
        )
    ) {
        BackButton(navController = envNavController, text = locale(backButtonTitle))
        LaunchedEffect(stationId) { sensorViewModel.initFetch(stationId, subCategoryId) }
        ScreenWrapper(masterDesignArgs, design, withTopBar = false, screenWrapperState = sensorViewModel.wrapperState ) {
            val subtitle = if (sensorViewModel.sensors.isNotEmpty()) sensorViewModel.sensors.first().station.name else ""
            EnvironmentSubtitle(subtitle)
            EnvironmentGrid(
                modifier = Modifier.fillMaxSize(),
                itemList = sensorViewModel.sensors,
                onClick = { item ->
                    envNavigateTo(
                        envNavController,
                        EnvironmentNavItems.EnvironmentNavItemSensorDetail,
                        "objectId" to item.objectId,
                        "backButtonTitle" to item.sensorType.name
                    )
                },
                backgroundColor = design.envMeasureCardBack,
                content = { item ->
                    EnvironmentSensorStationGridCard(item)
                },
            )
        }
    }
}
