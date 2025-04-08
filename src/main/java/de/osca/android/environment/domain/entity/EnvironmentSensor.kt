package de.osca.android.environment.domain.entity

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.attribute
import com.parse.ktx.delegates.doubleAttribute
import com.parse.ktx.delegates.stringAttribute
import de.osca.android.environment.domain.entity.delegate.dateAttribute
import java.util.Date

@ParseClassName("EnvironmentSensor")
class EnvironmentSensor : ParseObject() {
    val value: Double by doubleAttribute()
    val sensorType: EnvironmentSensorType by attribute()
    val station: EnvironmentStation by attribute()
    val refId: String by stringAttribute()
    val observedAt: Date? by dateAttribute()
    val graphDivider: List<Any>? by attribute()

    fun getDividers(): List<EnvironmentGraphDivider> {
        val dividers = emptyList<EnvironmentGraphDivider>().toMutableList()
        graphDivider?.let { list ->
            list.forEach { divider ->
                (divider as? HashMap<String, Any>)?.let { dividerMap ->
                    val name = dividerMap["name"] as? String
                    var value = dividerMap["value"] as? Double
                    // .0 values are sometimes considered as Int for whatever reason
                    if (value == null) {
                        value = (dividerMap["value"] as? Int)?.toDouble()
                    }
                    val color = dividerMap["color"] as? String
                    if (name != null && value != null && color != null) {
                        dividers += EnvironmentGraphDivider(name, value, color)
                    }
                }
            }
        }
        return dividers
    }
}
