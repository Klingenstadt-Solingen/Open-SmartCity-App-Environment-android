package de.osca.android.environment.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.osca.android.environment.R
import de.osca.android.environment.domain.entity.EnvironmentGraphStatus
import de.osca.android.environment.domain.entity.EnvironmentSensor
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentSensorDetailViewModel
import de.osca.android.essentials.presentation.component.design.LoadingScreen
import de.osca.android.essentials.presentation.component.design.LocalMasterDesignArgs

@Composable
fun EnvironmentGraphWrapper(
    sensorDetailViewModel: EnvironmentSensorDetailViewModel = hiltViewModel(),
    sensor: EnvironmentSensor,
) {
    val design = LocalEnvironmentDesignArgs.current
    val masterDesignArgs = LocalMasterDesignArgs.current
    val refId = sensorDetailViewModel.sensor.value?.refId ?: ""
    LaunchedEffect(refId) {
        sensorDetailViewModel.fetchGraphData(refId)
    }

    when (sensorDetailViewModel.sensorDataStatus.value) {
        is EnvironmentGraphStatus.Loading -> {
            LoadingScreen(masterDesignArgs = masterDesignArgs, moduleDesignArgs = design)
        }

        is EnvironmentGraphStatus.Loaded -> {
            EnvironmentGraph(
                values = (sensorDetailViewModel.sensorDataStatus.value as EnvironmentGraphStatus.Loaded).data,
                sensor = sensor,
            )
        }

        is EnvironmentGraphStatus.Error -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(10.dp)) {
                var error =
                    (sensorDetailViewModel.sensorDataStatus.value as EnvironmentGraphStatus.Error).error
                if (error == "no_data") {
                    error = stringResource(id = R.string.error_no_data_available)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = error)
                    Button(onClick = {
                        sensorDetailViewModel.sensorDataStatus.value =
                            EnvironmentGraphStatus.Loading
                        sensorDetailViewModel.fetchGraphData(refId)
                    }) {
                        Text(text = stringResource(id = R.string.try_again_button))
                    }
                }
            }
        }
    }
}
