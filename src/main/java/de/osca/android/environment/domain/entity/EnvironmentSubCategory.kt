package de.osca.android.environment.domain.entity

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseRelation
import com.parse.ktx.delegates.attribute
import com.parse.ktx.delegates.relationAttribute
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("EnvironmentSubCategory")
class EnvironmentSubCategory: ParseObject() {
    val name: String by stringAttribute()

    val sensorTypes: ParseRelation<EnvironmentSensorType> by relationAttribute()

    val order: String by stringAttribute()

    val icon: EnvironmentIcon by attribute()
}
