package de.osca.android.environment.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.osca.android.environment.R
import de.osca.android.environment.domain.entity.EnvironmentSensor
import de.osca.android.environment.navigation.EnvironmentNavItems
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.environment.presentation.components.EnvironmentAsyncImage
import de.osca.android.environment.presentation.environment.subscreen.EnvironmentSensorValueDetailScreen
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentLocaleViewModel
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentViewModel
import de.osca.android.environment.presentation.environment.viewmodel.LocalEnvironmentLocale
import de.osca.android.environment.presentation.environment.viewmodel.locale
import de.osca.android.essentials.presentation.component.design.BaseListContainer
import de.osca.android.essentials.presentation.component.design.LocalMasterDesignArgs
import de.osca.android.essentials.presentation.component.design.MasterDesignArgs
import kotlin.math.ceil

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun EnvironmentWidget(
    navController: NavController,
    @StringRes cityName: Int = -1,
    isMocked: Boolean = false,
    // initialLocation: LatLng,
    environmentViewModel: EnvironmentViewModel = hiltViewModel(),
    localeViewModel: EnvironmentLocaleViewModel = hiltViewModel(),
    masterDesignArgs: MasterDesignArgs = environmentViewModel.defaultDesignArgs,
) {
    if (environmentViewModel.environmentDesignArgs.vIsWidgetVisible) {
        LaunchedEffect(Unit) {
            environmentViewModel.initFetch()
        }
        LaunchedEffect(Locale.current.language) {
            localeViewModel.language = ("${Locale.current.language}-${Locale.current.region}")
            localeViewModel.initFetch()
        }
        val design = environmentViewModel.environmentDesignArgs
        val windowSize =
            if (environmentViewModel.activatedSensors.size <= 6) {
                3
            } else {
                ceil(environmentViewModel.activatedSensors.size / 2f).toInt()
            }

        val defaultColor =
            Color(
                0.18431373f,
                0.39215687f,
                0.69411767f,
                1.0f,
            )
        val showBottomSheet = remember { mutableStateOf(false) }
        val sensor = remember { mutableStateOf<EnvironmentSensor?>(null) }
        val state = rememberScrollState()
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val showDeleteIcon = remember { mutableStateOf(false) }
        val interactionSource = remember { MutableInteractionSource() }
        CompositionLocalProvider(
            LocalEnvironmentDesignArgs provides design,
            LocalMasterDesignArgs provides masterDesignArgs,
            LocalEnvironmentLocale provides localeViewModel,
        ) {
            BaseListContainer(
                text = stringResource(design.vWidgetTitle, stringResource(cityName)),
                showMoreOption = design.vWidgetShowMoreOption,
                moduleDesignArgs = design,
                onMoreOptionClick = {
                    navController.navigate(EnvironmentNavItems.EnvironmentNavItem.route)
                },
                masterDesignArgs = masterDesignArgs,
            ) {
                Column(
                    modifier =
                        Modifier.fillMaxWidth().horizontalScroll(state).clickable(
                            interactionSource = interactionSource,
                            indication = null,
                        ) {
                            showDeleteIcon.value = false
                        },
                ) {
                    if (environmentViewModel.activatedSensors.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.environment_no_favorites),
                            style =
                                TextStyle(
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Center,
                                ),
                        )
                    } else {
                        environmentViewModel.activatedSensors
                            .windowed(
                                windowSize,
                                step = windowSize,
                                partialWindows = true,
                            )
                            .map { partial ->
                                Row(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth(),
                                ) {
                                    partial.map { item ->
                                        Box {
                                            Card(
                                                shape = RoundedCornerShape(masterDesignArgs.mShapeCard),
                                                backgroundColor = masterDesignArgs.mCardBackColor,
                                                elevation = masterDesignArgs.mCardElevation,
                                                modifier =
                                                    Modifier
                                                        .size(135.dp)
                                                        .padding(4.dp)
                                                        .combinedClickable(
                                                            interactionSource = if (showDeleteIcon.value) interactionSource else null,
                                                            indication = ripple(),
                                                            onClick = {
                                                                if (!showDeleteIcon.value) {
                                                                    sensor.value = item
                                                                    showBottomSheet.value = true
                                                                }
                                                                showDeleteIcon.value = false
                                                            },
                                                            onLongClick = {
                                                                showDeleteIcon.value = true
                                                            },
                                                        ),
                                            ) {
                                                Column(
                                                    modifier =
                                                        Modifier
                                                            .width(IntrinsicSize.Max)
                                                            .padding(masterDesignArgs.mContentPaddingForMiniCards),
                                                    verticalArrangement = Arrangement.SpaceBetween,
                                                ) {
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(1f),
                                                        verticalAlignment = Alignment.Top,
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                    ) {
                                                        EnvironmentAsyncImage(
                                                            url = item.sensorType.icon.icon.url,
                                                            modifier =
                                                                Modifier
                                                                    .size(40.dp),
                                                            color =
                                                                masterDesignArgs.mWidgetShowMoreTextColor
                                                                    ?: defaultColor,
                                                            description = item.sensorType.icon.definition,
                                                        )
                                                        Text(
                                                            text = item.sensorType.unit,
                                                            fontSize = 15.sp,
                                                            color =
                                                                masterDesignArgs.mWidgetShowMoreTextColor
                                                                    ?: defaultColor,
                                                        )
                                                    }
                                                    Row(modifier = Modifier.fillMaxWidth()) {
                                                        Column {
                                                            Text(
                                                                modifier = Modifier.padding(top = 2.dp),
                                                                text = "%.2f".format(item.value),
                                                                fontSize = 23.sp,
                                                                color =
                                                                    masterDesignArgs.mWidgetShowMoreTextColor
                                                                        ?: defaultColor,
                                                            )
                                                            Text(
                                                                text = item.station.name,
                                                                fontSize = 10.sp,
                                                                color =
                                                                    masterDesignArgs.mWidgetShowMoreTextColor
                                                                        ?: defaultColor,
                                                                maxLines = 1,
                                                                overflow = TextOverflow.Ellipsis,
                                                            )
                                                            Text(
                                                                text = locale(item.sensorType.name),
                                                                fontSize = 10.sp,
                                                                color =
                                                                    masterDesignArgs.mWidgetShowMoreTextColor
                                                                        ?: defaultColor,
                                                                maxLines = 1,
                                                                overflow = TextOverflow.Ellipsis,
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                            if (showDeleteIcon.value) {
                                                IconButton(
                                                    onClick = {
                                                        environmentViewModel.deactivateSensor(item.objectId)
                                                    },
                                                    modifier = Modifier.offset(100.dp, (-8).dp),
                                                ) {
                                                    Icon(
                                                        painter = painterResource(R.drawable.ic_close_circle),
                                                        null,
                                                        tint = Color.Red,
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                    }
                    if (showBottomSheet.value) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                showBottomSheet.value = false
                            },
                            sheetState = sheetState,
                            containerColor = Color.White,
                        ) {
                            sensor.value?.let { value ->
                                EnvironmentSensorValueDetailScreen(
                                    sensorId = value.objectId,
                                    envNavController = navController,
                                    environmentViewModel = environmentViewModel,
                                    backButtonTitle = "",
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
