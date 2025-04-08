package de.osca.android.environment.presentation.environment.subscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.osca.android.environment.R
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.environment.presentation.components.BackButton
import de.osca.android.environment.presentation.components.EnvironmentAsyncImage
import de.osca.android.environment.presentation.components.EnvironmentGraphWrapper
import de.osca.android.environment.presentation.components.EnvironmentMap
import de.osca.android.environment.presentation.components.EnvironmentSubtitle
import de.osca.android.environment.presentation.components.EnvironmentValueUnit
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentSensorDetailViewModel
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentViewModel
import de.osca.android.environment.presentation.environment.viewmodel.locale
import de.osca.android.essentials.presentation.component.design.LocalMasterDesignArgs
import de.osca.android.essentials.presentation.component.screen_wrapper.ScreenWrapper

@Composable
fun EnvironmentSensorValueDetailScreen(
    environmentViewModel: EnvironmentViewModel,
    sensorId: String,
    envNavController: NavController,
    sensorDetailViewModel: EnvironmentSensorDetailViewModel = hiltViewModel(),
    backButtonTitle: String,
) {
    val design = LocalEnvironmentDesignArgs.current
    val masterDesignArgs = LocalMasterDesignArgs.current
    val sensorValue = sensorDetailViewModel.sensor.value
    val activeFavorite =
        remember {
            mutableStateOf(false)
        }
    activeFavorite.value =
        environmentViewModel.activatedSensors.find { it.objectId == sensorId } != null
    LaunchedEffect(sensorId) {
        sensorDetailViewModel.initFetch(sensorId)
    }
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    PaddingValues(
                        horizontal = design.mRootBoarderSpacing ?: masterDesignArgs.mBorderSpace,
                    ),
                ),
    ) {
        if (backButtonTitle != "") {
            BackButton(
                envNavController,
                text = locale(backButtonTitle),
            )
        }

        ScreenWrapper(
            masterDesignArgs,
            design,
            withTopBar = false,
            screenWrapperState = sensorDetailViewModel.wrapperState,
        ) {
            EnvironmentSubtitle(sensorValue?.station?.name ?: "")
            Spacer(Modifier.size(design.envMeasurementDetailSpacerSizeSmall))
            LazyColumn {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(design.envMeasurementDetailRowSpacing),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                EnvironmentAsyncImage(
                                    url = sensorValue?.sensorType?.icon?.icon?.url ?: "",
                                    modifier = Modifier.size(design.envMeasurementDetailIconSize),
                                    color = design.envMeasurementDetailIconColor,
                                )
                                var formattedValue = ""
                                sensorValue?.let {
                                    formattedValue = "%.2f".format(it.value)
                                }
                                EnvironmentValueUnit(
                                    formattedValue,
                                    sensorValue?.sensorType?.unit ?: "",
                                )
                            }
                        }
                        Column {
                            IconButton(onClick = {
                                if (!activeFavorite.value) {
                                    sensorDetailViewModel.sensor.value?.let {
                                        environmentViewModel.activateSensor(it)
                                        activeFavorite.value = true
                                    }
                                } else {
                                    environmentViewModel.deactivateSensor(
                                        sensorId,
                                    )
                                    activeFavorite.value = false
                                }
                            }) {
                                Icon(
                                    painter =
                                        if (activeFavorite.value) {
                                            painterResource(R.drawable.ic_fav_enabled)
                                        } else {
                                            painterResource(R.drawable.ic_fav_disabled)
                                        },
                                    contentDescription = "",
                                    modifier =
                                        Modifier
                                            .padding(bottom = 20.dp, top = 10.dp)
                                            .height(30.dp)
                                            .width(30.dp),
                                    tint = design.envValueTextColor,
                                )
                            }
                        }
                    }
                    Spacer(Modifier.size(design.envMeasurementDetailSpacerSizeSmall))
                }
                item {
                    Card(
                        backgroundColor = design.envMeasurementDetailCardColor,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.5f),
                        shape =
                            RoundedCornerShape(
                                design.mShapeCard ?: masterDesignArgs.mShapeCard,
                            ),
                        elevation = design.envDefaultElevation,
                    ) {
                        EnvironmentGraphWrapper(sensor = sensorValue!!)
                    }
                    Spacer(Modifier.size(design.envMeasurementDetailSpacerSizeBig))
                }
                item {
                    Text(
                        locale(sensorValue?.sensorType?.definition ?: "lorem_ipsum"),
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(Modifier.size(design.envMeasurementDetailSpacerSizeBig))
                }
                item {
                    Text(
                        text = stringResource(R.string.environment_title_sensor_location),
                        modifier = Modifier.fillMaxWidth(),
                        color = design.envMeasurementDetailTextColor,
                        fontWeight = design.envMeasurementDetailTextFontWeight,
                    )

                    Spacer(Modifier.size(design.envMeasurementDetailSpacerSizeMedium))

                    Card(
                        backgroundColor = design.envMeasurementDetailCardColor,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                        shape =
                            RoundedCornerShape(
                                design.mShapeCard ?: masterDesignArgs.mShapeCard,
                            ),
                        elevation = design.mCardElevation ?: masterDesignArgs.mCardElevation,
                    ) {
                        EnvironmentMap(
                            sensorDetailViewModel.sensor.value?.station,
                            initZoom = 16f,
                        )
                    }
                    Spacer(Modifier.size(design.envMeasurementDetailSpacerSizeBig))
                }
            }
        }
    }
}
