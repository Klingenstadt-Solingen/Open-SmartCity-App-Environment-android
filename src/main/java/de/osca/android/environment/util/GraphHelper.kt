package de.osca.android.environment.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import de.osca.android.environment.domain.entity.EnvironmentGraphDivider
import de.osca.android.environment.domain.entity.EnvironmentSensorValue
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.StrokeStyle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

fun hexToColor(hex: String): Color {
    val string = hex.removePrefix("#")
    val colorInt =
        when (string.length) {
            6 -> (0xFF shl 24) or string.toLong(16).toInt()
            8 -> string.toLong(16).toInt()
            else -> 0
        }
    return Color(colorInt)
}

fun graphLabels(values: List<EnvironmentSensorValue>): List<String> {
    val min = values.first().observedAt ?: Date()
    val max = values.last().observedAt ?: Date()
    return listOf(
        SimpleDateFormat("HH:mm", Locale.GERMAN).format(min),
        SimpleDateFormat("HH:mm", Locale.GERMAN).format(max),
    )
}

fun dividerLine(
    label: String,
    value: Double,
    dataCount: Int,
    color: Color,
): Line {
    val data = mutableListOf<Double>()
    repeat(dataCount) {
        data += value
    }
    return Line(
        label = label,
        values = data,
        color = SolidColor(color),
        drawStyle =
            DrawStyle.Stroke(
                width = 3.dp,
                strokeStyle =
                    StrokeStyle.Dashed(
                        intervals = floatArrayOf(10f, 10f),
                        phase = 15f,
                    ),
            ),
    )
}

fun graphPaddedMinMax(
    data: List<Double>,
    divider: List<EnvironmentGraphDivider>,
    steps: Int = 5,
): Pair<Double, Double> {
    var min = (data.minOrNull() ?: 0.0).roundToInt() - 1
    var max = (data.maxOrNull() ?: 10.0).roundToInt() + 1

    if (divider.isNotEmpty()) {
        val minGraph = divider.minBy { it.value }.value.roundToInt() - 1
        val maxGraph = divider.maxBy { it.value }.value.roundToInt() + 1
        if (minGraph < min) {
            min = minGraph
        }
        if (maxGraph > max) {
            max = maxGraph
        }
    }

    while ((max - min) % (steps - 1) != 0) {
        min--
        if ((max - min) % (steps - 1) != 0) {
            max++
        }
    }

    return Pair(min.toDouble(), max.toDouble())
}
