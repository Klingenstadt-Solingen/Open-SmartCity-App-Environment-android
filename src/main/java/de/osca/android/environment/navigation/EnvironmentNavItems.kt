package de.osca.android.environment.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import de.osca.android.essentials.domain.entity.navigation.NavigationItem

sealed class EnvironmentNavItems {
    object EnvironmentNavItem : NavigationItem(
        title = -1,
        route = "environment",
        icon = -1,
    )

    object EnvironmentNavItemFullMap : NavigationItem(
        title = -1,
        route = "environment/full_map",
        icon = -1,
    )

    object EnvironmentNavItemTab : NavigationItem(
        title = -1,
        route = "environment_tab/{objectId}/{color}",
        arguments =
            listOf(
                navArgument("objectId") { type = NavType.StringType },
                navArgument("color") { type = NavType.StringType },
            ),
        icon = -1,
    ) {
        fun getDefaultRoute(): String {
            return "environment_tab//"
        }
    }

    object EnvironmentNavItemStation : NavigationItem(
        title = -1,
        route = "environment_station/{objectId}/{backButtonTitle}/{selectedTab}",
        arguments =
            listOf(
                navArgument("objectId") { type = NavType.StringType },
                navArgument("backButtonTitle") { type = NavType.StringType },
                navArgument("selectedTab") { type = NavType.StringType },
            ),
        icon = -1,
    )

    object EnvironmentNavItemSensorGrid : NavigationItem(
        title = -1,
        route = "environment_sensor_grid/{objectId}/{subCategoryId}/{backButtonTitle}",
        arguments =
            listOf(
                navArgument("objectId") { type = NavType.StringType },
                navArgument("subCategoryId") { type = NavType.StringType },
                navArgument("backButtonTitle") { type = NavType.StringType },
            ),
        icon = -1,
    )

    object EnvironmentNavItemSensorDetail : NavigationItem(
        title = -1,
        route = "environment_sensor_detail/{objectId}/{backButtonTitle}",
        arguments =
            listOf(
                navArgument("objectId") { type = NavType.StringType },
                navArgument("backButtonTitle") { type = NavType.StringType },
            ),
        icon = -1,
    )
}
