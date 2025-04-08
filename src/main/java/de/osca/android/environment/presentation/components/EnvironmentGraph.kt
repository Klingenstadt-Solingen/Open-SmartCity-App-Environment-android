package de.osca.android.environment.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.osca.android.environment.domain.entity.EnvironmentSensor
import de.osca.android.environment.domain.entity.EnvironmentSensorValue
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentLocaleViewModel
import de.osca.android.environment.util.dividerLine
import de.osca.android.environment.util.graphLabels
import de.osca.android.environment.util.graphPaddedMinMax
import de.osca.android.environment.util.hexToColor
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.extensions.format
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line

@Composable
fun EnvironmentGraph(
    values: List<EnvironmentSensorValue> = emptyList(),
    sensor: EnvironmentSensor,
    localeViewModel: EnvironmentLocaleViewModel = hiltViewModel(),
) {
    val design = LocalEnvironmentDesignArgs.current
    val valueData = values.map { it.value }
    val dividers = sensor.getDividers().plus(sensor.sensorType.getDividers())
    val lines =
        remember {
            val lineList = mutableListOf<Line>()
            lineList +=
                Line(
                    label = "Values",
                    values = valueData,
                    color = SolidColor(design.envMeasurementDetailGraphLineColor),
                    firstGradientFillColor = design.envMeasurementDetailGraphGradientFirstColor,
                    secondGradientFillColor = design.envMeasurementDetailGraphGradientSecondColor,
                )
            dividers.forEach {
                lineList +=
                    dividerLine(
                        label = localeViewModel.locale(it.name),
                        value = it.value,
                        dataCount = values.size,
                        color = hexToColor(it.color),
                    )
            }
            lineList
        }
    val minMax = graphPaddedMinMax(valueData, dividers)
    val labels = graphLabels(values)

    LineChart(
        modifier = Modifier.padding(20.dp),
        data = lines,
        indicatorProperties =
            HorizontalIndicatorProperties(contentBuilder = {
                it.format(0)
            }),
        labelProperties =
            LabelProperties(
                enabled = true,
                labels = labels,
            ),
        labelHelperProperties = LabelHelperProperties(enabled = true),
        minValue = minMax.first,
        maxValue = minMax.second,
    )
}
