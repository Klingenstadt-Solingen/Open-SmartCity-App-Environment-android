package de.osca.android.environment.presentation.components

import androidx.navigation.NavController
import de.osca.android.essentials.domain.entity.navigation.NavigationItem


fun envGetCurrentRoute(envNavController: NavController): String? {
    return envNavController.currentBackStackEntry?.destination?.route
}

fun envNavigateTo(envNavController: NavController, item: NavigationItem, vararg pairs: Pair<String, String>, replaceBackStackEntry: Boolean = false) {
    envNavigateTo(envNavController, item, pairs.toMap(), replaceBackStackEntry)
}



fun envNavigateTo(envNavController: NavController, item: NavigationItem, argMap: Map<String, String>? = null, replaceBackStackEntry: Boolean = false,) {
    var argRoute = item.route
    argMap?.entries?.forEach { entry ->
        argRoute = argRoute.replace("{${entry.key}}",entry.value) }
    envNavController.navigate(argRoute) {
        popUpTo(envGetCurrentRoute(envNavController) ?: argRoute) {
            inclusive = replaceBackStackEntry
        }
        launchSingleTop = true
    }
}
