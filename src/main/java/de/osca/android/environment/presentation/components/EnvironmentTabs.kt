package de.osca.android.environment.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import de.osca.android.environment.navigation.EnvironmentNavItems
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentViewModel
import de.osca.android.environment.presentation.environment.viewmodel.locale
import de.osca.android.essentials.presentation.component.design.LocalMasterDesignArgs

@Composable
fun EnvironmentTabs(
    envNavController: NavController,
    environmentViewModel: EnvironmentViewModel,
) {
    val masterDesignArgs = LocalMasterDesignArgs.current
    val design = LocalEnvironmentDesignArgs.current
    val currentTab = remember { mutableIntStateOf(0) }
    var tabbackground: Color
    val halfTabPadding = design.envTabPadding / 2
    val mRootBoarderSpacing = design.mRootBoarderSpacing ?: masterDesignArgs.mRootBoarderSpacing
    val items = environmentViewModel.categories
    TabRow(
        modifier =
            Modifier
                .padding(bottom = design.envTabRowBottomPadding)
                .background(color = design.envTabBackgroundColor),
        selectedTabIndex = currentTab.intValue,
        backgroundColor = design.envTabBackgroundColor,
        contentColor = design.envTabBackgroundColor,
    ) {
        items.forEachIndexed { index, item ->
            items.lastIndex
            var modifier =
                when (index) {
                    0 -> {
                        Modifier.padding(start = mRootBoarderSpacing, end = halfTabPadding)
                    }

                    items.lastIndex -> {
                        Modifier.padding(start = halfTabPadding, end = mRootBoarderSpacing)
                    }

                    else -> {
                        Modifier.padding(horizontal = halfTabPadding)
                    }
                }.padding(top = halfTabPadding)

            tabbackground =
                if (currentTab.intValue == index) {
                    modifier =
                        modifier.shadow(
                            design.envTabBackgroundShadowElevation,
                            shape = design.envTabShape,
                        )
                    design.envTabSelectedColor
                } else {
                    design.envTabUnselectedColor
                }
            Tab(
                text = {
                    Text(
                        text = locale(item.name),
                        fontSize = design.envTabTextFontSize,
                        style = masterDesignArgs.normalTextStyle,
                        fontWeight = design.envTabTextFontWeight,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                selected = currentTab.intValue == index,
                onClick = {
                    currentTab.intValue = index
                    envNavigateTo(
                        envNavController,
                        EnvironmentNavItems.EnvironmentNavItemTab,
                        "objectId" to item.objectId,
                        "color" to item.color,
                        replaceBackStackEntry = true,
                    )
                },
                modifier =
                    modifier
                        .background(color = tabbackground, shape = design.envTabShape),
            )
        }
    }
}
