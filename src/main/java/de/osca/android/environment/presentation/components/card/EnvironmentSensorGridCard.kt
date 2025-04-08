package de.osca.android.environment.presentation.components.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import de.osca.android.environment.domain.entity.EnvironmentSensor
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.environment.presentation.components.EnvironmentAsyncImage
import de.osca.android.environment.presentation.environment.viewmodel.locale
import de.osca.android.essentials.presentation.component.design.LocalMasterDesignArgs

@Composable
fun EnvironmentSensorStationGridCard(item: EnvironmentSensor) {
    val design = LocalEnvironmentDesignArgs.current
    val masterDesignArgs = LocalMasterDesignArgs.current
    Box(
        modifier =
            Modifier.padding(
                design.mCardContentPadding ?: masterDesignArgs.mCardContentPadding,
            ),
    ) {
        Box(
            modifier =
                Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
                    .wrapContentHeight(),
        ) {
            EnvironmentAsyncImage(
                url = item.sensorType.icon.icon.url,
                modifier =
                    Modifier.align(Alignment.TopStart)
                        .size(design.envMeasureCardIconSize),
                color = design.envMeasureCardIconColor,
                description = item.sensorType.icon.definition,
            )
            Text(
                text = item.sensorType.unit,
                modifier =
                    Modifier
                        .align(Alignment.TopEnd),
                fontSize = design.envUnitTextSize,
                color = design.envUnitTextColor,
            )
        }
        Column(modifier = Modifier.align(Alignment.BottomStart)) {
            Text(
                modifier = Modifier.padding(top = design.envUnitTopPadding),
                text = "%.2f".format(item.value),
                fontSize = design.envValueTextSize,
                color = design.envValueTextColor,
            )
            Text(
                text = locale(item.sensorType.name),
                fontSize = design.envMeasureCardTitleTextSize,
                color = design.envMeasureCardTitleTextColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
