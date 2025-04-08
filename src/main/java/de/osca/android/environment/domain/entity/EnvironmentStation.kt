package de.osca.android.environment.domain.entity

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.attribute
import com.parse.ktx.delegates.stringAttribute
import de.osca.android.essentials.domain.entity.ParsePOI

@ParseClassName("EnvironmentStation")
class EnvironmentStation : ParseObject() {
    val name: String by stringAttribute()

    val poi: ParsePOI by attribute()
}
