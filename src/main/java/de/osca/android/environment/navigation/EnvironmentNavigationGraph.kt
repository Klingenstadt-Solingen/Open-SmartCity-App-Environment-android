package de.osca.android.environment.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import de.osca.android.environment.presentation.environment.subscreen.EnvironmentFullScreenMapScreen
import de.osca.android.environment.presentation.environment.subscreen.EnvironmentSensorCategoryGridScreen
import de.osca.android.environment.presentation.environment.subscreen.EnvironmentSensorGridScreen
import de.osca.android.environment.presentation.environment.subscreen.EnvironmentSensorValueDetailScreen
import de.osca.android.environment.presentation.environment.subscreen.EnvironmentStationScreen
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentViewModel
import de.osca.android.essentials.utils.extensions.composableForNav

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EnvironmentNavigationGraph(
    envNavController: NavHostController,
    environmentViewModel: EnvironmentViewModel,
) {
    CompositionLocalProvider(LocalNavigationController provides envNavController) {
        AnimatedNavHost(
            modifier = Modifier.fillMaxSize(),
            navController = envNavController,
            startDestination = EnvironmentNavItems.EnvironmentNavItemTab.getDefaultRoute(),
        ) {
            /** TABS */
            composableForNav(EnvironmentNavItems.EnvironmentNavItemTab) {
                val startId = environmentViewModel.categories.first().objectId
                var categoryId = it.arguments?.getString("objectId") ?: startId
                if (categoryId.isBlank()) {
                    categoryId = startId
                }
                val startColor = environmentViewModel.categories.first().color
                var color =
                    it.arguments?.getString("color")
                        ?: startColor
                if (color.isBlank()) {
                    color = startColor
                }
                EnvironmentSensorCategoryGridScreen(
                    envNavController,
                    categoryId,
                    color,
                )
            }

            /** MAP*/
            composableForNav(EnvironmentNavItems.EnvironmentNavItemFullMap) {
                EnvironmentFullScreenMapScreen(
                    envNavController,
                    showBottomSheet = null,
                )
            }

            /** STATION */
            composableForNav(EnvironmentNavItems.EnvironmentNavItemStation) {
                val subCategoryId = it.arguments?.getString("objectId") ?: ""
                val backButtonTitle = it.arguments?.getString("backButtonTitle") ?: ""
                val selectedTab = it.arguments?.getString("selectedTab") ?: ""
                EnvironmentStationScreen(
                    envNavController,
                    EnvironmentNavItems.EnvironmentNavItemSensorGrid,
                    subCategoryId,
                    selectedTab,
                    backButtonTitle,
                )
            }

            /** MEASUREMENTGRID */
            composableForNav(EnvironmentNavItems.EnvironmentNavItemSensorGrid) {
                val locationId = it.arguments?.getString("objectId") ?: ""
                val subCategoryId = it.arguments?.getString("subCategoryId") ?: ""
                val backButtonTitle = it.arguments?.getString("backButtonTitle") ?: ""
                EnvironmentSensorGridScreen(
                    envNavController,
                    locationId,
                    subCategoryId,
                    backButtonTitle,
                )
            }

            /** GRAPH */
            composableForNav(EnvironmentNavItems.EnvironmentNavItemSensorDetail) {
                val sensorValueId = it.arguments?.getString("objectId") ?: ""
                val backButtonTitle = it.arguments?.getString("backButtonTitle") ?: ""
                EnvironmentSensorValueDetailScreen(
                    environmentViewModel,
                    sensorValueId,
                    envNavController,
                    backButtonTitle = backButtonTitle,
                )
            }
        }
    }
}
