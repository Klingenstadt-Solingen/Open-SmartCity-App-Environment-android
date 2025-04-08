package de.osca.android.environment.presentation.environment

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import de.osca.android.environment.navigation.EnvironmentNavigationGraph
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.environment.presentation.components.EnvironmentTabs
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentLocaleViewModel
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentViewModel
import de.osca.android.environment.presentation.environment.viewmodel.LocalEnvironmentLocale
import de.osca.android.essentials.presentation.component.design.LocalMasterDesignArgs
import de.osca.android.essentials.presentation.component.screen_wrapper.ScreenWrapper
import de.osca.android.essentials.presentation.component.topbar.ScreenTopBar
import de.osca.android.essentials.utils.extensions.SetSystemStatusBar

/**
 * Main Module Screen
 *
// @param isMocked
 */

@Composable
fun EnvironmentScreen(
    navController: NavController,
    // isMocked: Boolean = false,
    environmentViewModel: EnvironmentViewModel = hiltViewModel(),
    localeViewModel: EnvironmentLocaleViewModel = hiltViewModel(),
) {
    LaunchedEffect(Locale.current.language) {
        localeViewModel.language = ("${Locale.current.language}-${Locale.current.region}")
        localeViewModel.initFetch()
    }
    val masterDesignArgs = environmentViewModel.defaultDesignArgs
    val design = environmentViewModel.environmentDesignArgs
    val envNavController = rememberNavController()
    SetSystemStatusBar(
        !(design.mIsStatusBarWhite ?: masterDesignArgs.mIsStatusBarWhite),
        Color.Transparent,
    )
    CompositionLocalProvider(
        LocalEnvironmentDesignArgs provides design,
        LocalMasterDesignArgs provides masterDesignArgs,
        LocalEnvironmentLocale provides localeViewModel,
    ) {
        ScreenWrapper(
            topBar = {
                ScreenTopBar(
                    title = stringResource(id = design.vModuleTitle),
                    navController = navController,
                    overrideTextColor = design.mTopBarTextColor,
                    overrideBackgroundColor = design.mTopBarBackColor,
                    masterDesignArgs = masterDesignArgs,
                )
            },
            screenWrapperState = localeViewModel.wrapperState,
            retryAction = {
                localeViewModel.initFetch()
            },
            masterDesignArgs = masterDesignArgs,
            moduleDesignArgs = design,
        ) {
            LaunchedEffect(Unit) {
                environmentViewModel.initFetch()
            }
            ScreenWrapper(
                withTopBar = false,
                screenWrapperState = environmentViewModel.wrapperState,
                retryAction = {
                    environmentViewModel.initFetch()
                },
                masterDesignArgs = masterDesignArgs,
                moduleDesignArgs = design,
            ) {
                Column {
                    EnvironmentTabs(envNavController, environmentViewModel)
                    EnvironmentNavigationGraph(envNavController, environmentViewModel)
                }
            }
        }
    }
}
