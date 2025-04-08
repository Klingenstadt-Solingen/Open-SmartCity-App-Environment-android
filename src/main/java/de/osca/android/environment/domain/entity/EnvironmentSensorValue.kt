package de.osca.android.environment.domain.entity

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.doubleAttribute
import de.osca.android.environment.domain.entity.delegate.dateAttribute
import java.util.*

@ParseClassName("EnvironmentSensorValue")
class EnvironmentSensorValue: ParseObject() {
    val value: Double by doubleAttribute()
    val observedAt: Date? by dateAttribute()
}
