package de.osca.android.environment.presentation.environment.subscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.osca.android.environment.R
import de.osca.android.environment.navigation.EnvironmentNavItems
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.environment.presentation.components.card.EnvironmentTabGridCard
import de.osca.android.environment.presentation.components.envNavigateTo
import de.osca.android.environment.presentation.components.grid.EnvironmentGrid
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentSubCategoryGridViewModel
import de.osca.android.essentials.presentation.component.design.LocalMasterDesignArgs
import de.osca.android.essentials.presentation.component.screen_wrapper.ScreenWrapper

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnvironmentSensorCategoryGridScreen(
    envNavController: NavController,
    categoryId: String,
    color: String,
    subCategoryViewModel: EnvironmentSubCategoryGridViewModel = hiltViewModel(),
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showBottomSheet = remember { mutableStateOf(false) }

    val design = LocalEnvironmentDesignArgs.current
    val masterDesignArgs = LocalMasterDesignArgs.current
    val subCategoryColor = remember { mutableStateOf<Color?>(design.mCardBackColor) }

    LaunchedEffect(categoryId) {
        subCategoryViewModel.initFetch(categoryId)
        try {
            subCategoryColor.value = Color(android.graphics.Color.parseColor(color))
        } catch (_: Exception){
            subCategoryColor.value = design.mCardBackColor
        }
    }

    Scaffold(
        topBar = { },
        floatingActionButton = {
            IconButton(
                modifier =
                    Modifier.background(Color(242, 242, 242), RoundedCornerShape(10.dp)),
                onClick = {
                    showBottomSheet.value = showBottomSheet.value.not()
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_map_with_marker),
                    contentDescription = null,
                    modifier = Modifier.size(55.dp).padding(10.dp),
                    tint = Color(0, 67, 115),
                )
            }

            if (showBottomSheet.value) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet.value = false
                    },
                    sheetState = sheetState,
                    containerColor = Color.White,
                    dragHandle = {
                        Surface(
                            modifier =
                                Modifier
                                    .padding(vertical = 10.dp),
                            color = Color.Gray,
                            shape = MaterialTheme.shapes.extraLarge,
                        ) {
                            Box(
                                Modifier
                                    .size(
                                        width = 32.dp,
                                        height = 4.dp,
                                    ),
                            )
                        }
                    },
                ) {
                    EnvironmentFullScreenMapScreen(
                        envNavController = envNavController,
                        showBottomSheet = showBottomSheet,
                    )
                }
            }
        },
    ) {
        ScreenWrapper(
            masterDesignArgs,
            design,
            withTopBar = false,
            screenWrapperState = subCategoryViewModel.wrapperState,
        ) {
            EnvironmentGrid(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = design.mRootBoarderSpacing ?: masterDesignArgs.mBorderSpace,
                        ),
                itemList = subCategoryViewModel.subCategories,
                onClick = { item ->
                    envNavigateTo(
                        envNavController,
                        EnvironmentNavItems.EnvironmentNavItemStation,
                        "objectId" to item.objectId,
                        "backButtonTitle" to item.name,
                        "selectedTab" to "list",
                    )
                },
                backgroundColor = subCategoryColor.value,
                content = { item ->
                    EnvironmentTabGridCard(item)
                },
                fetchMore = { skip ->
                    subCategoryViewModel.fetchSensorSubCategories(categoryId, skip = skip)
                },
            )
        }
    }
}
