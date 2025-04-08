package de.osca.android.environment.presentation.environment.subscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import de.osca.android.environment.R
import de.osca.android.environment.navigation.EnvironmentNavItems
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.environment.presentation.components.*
import de.osca.android.environment.presentation.environment.viewmodel.locale
import de.osca.android.essentials.domain.entity.navigation.NavigationItem
import de.osca.android.essentials.presentation.component.design.LocalMasterDesignArgs

@Composable
fun EnvironmentStationScreen(
    envNavController: NavController,
    navigationItem: NavigationItem,
    subCategoryId: String,
    selectedTab: String,
    backButtonTitle: String
) {
    val design = LocalEnvironmentDesignArgs.current
    val masterDesignArgs = LocalMasterDesignArgs.current
    Column(
        modifier = Modifier.padding(
            PaddingValues(
                horizontal = design.mRootBoarderSpacing ?: masterDesignArgs.mBorderSpace
            )
        )
    )
    {
        BackButton(
            navController = envNavController,
            text = locale(backButtonTitle)
        )
        EnvironmentSubtitle(
            stringResource(R.string.environment_title_choose_measurement_location)
        )
        Spacer(Modifier.size(design.envMeasurementLocationSpacerSmall))
        TabRow(
            selectedTabIndex = 1,
            indicator = {},
            modifier = Modifier.background(color = design.envMeasurementLocationTabBackgroundColor),
            backgroundColor = design.envMeasurementLocationTabBackgroundColor,
            contentColor = design.envMeasurementLocationTabBackgroundColor
        ) {
            EnvironmentMeasurementTab(
                text = stringResource(R.string.environment_title_list),
                modifier = Modifier.padding(end = design.envMeasurementLocationTabPadding),
                selected = (selectedTab == "list"),
                onClick = {
                    if (selectedTab == "map") {
                        envNavigateTo(
                            envNavController, EnvironmentNavItems.EnvironmentNavItemStation,
                            "objectId" to subCategoryId,
                            "backButtonTitle" to backButtonTitle,
                            "selectedTab" to "list",
                            replaceBackStackEntry = true
                        )
                    }
                }
            )
            EnvironmentMeasurementTab(
                text = stringResource(R.string.environment_title_map),
                modifier = Modifier.padding(start = design.envMeasurementLocationTabPadding),
                selected = (selectedTab == "map"),
                onClick = {
                    if (selectedTab == "list") {
                        envNavigateTo(
                            envNavController, EnvironmentNavItems.EnvironmentNavItemStation,
                            "objectId" to subCategoryId,
                            "backButtonTitle" to backButtonTitle,
                            "selectedTab" to "map",
                            replaceBackStackEntry = true
                        )
                    }
                }
            )
        }
        Spacer(Modifier.size(design.envMeasurementLocationSpacerBig))
        when (selectedTab) {
            "map" -> {
                EnvironmentStationMap(
                    envNavController = envNavController,
                    navigationItem = navigationItem,
                    subCategoryId = subCategoryId,
                    backButtonTitle = backButtonTitle
                )
            }
            else -> {
                EnvironmentStationList(
                    envNavController = envNavController,
                    navigationItem = navigationItem,
                    subCategoryId = subCategoryId,
                    backButtonTitle = backButtonTitle
                )
            }
        }
    }
}
