package de.osca.android.environment.data

import com.parse.ParseObject
import de.osca.android.environment.domain.boundaries.EnvironmentParseRegistration
import de.osca.android.environment.domain.entity.EnvironmentCategory
import de.osca.android.environment.domain.entity.EnvironmentIcon
import de.osca.android.environment.domain.entity.EnvironmentLanguage
import de.osca.android.environment.domain.entity.EnvironmentLocale
import de.osca.android.environment.domain.entity.EnvironmentSensor
import de.osca.android.environment.domain.entity.EnvironmentSensorType
import de.osca.android.environment.domain.entity.EnvironmentSensorValue
import de.osca.android.environment.domain.entity.EnvironmentStation
import de.osca.android.environment.domain.entity.EnvironmentSubCategory

open class EnvironmentParseRegistrationImpl : EnvironmentParseRegistration {
    override fun registerSubclasses() {
        ParseObject.registerSubclass(EnvironmentCategory::class.java)
        ParseObject.registerSubclass(EnvironmentSubCategory::class.java)
        ParseObject.registerSubclass(EnvironmentSensorType::class.java)
        ParseObject.registerSubclass(EnvironmentSensor::class.java)
        ParseObject.registerSubclass(EnvironmentStation::class.java)
        ParseObject.registerSubclass(EnvironmentLocale::class.java)
        ParseObject.registerSubclass(EnvironmentIcon::class.java)
        ParseObject.registerSubclass(EnvironmentLanguage::class.java)
        ParseObject.registerSubclass(EnvironmentSensorValue::class.java)
    }
}
