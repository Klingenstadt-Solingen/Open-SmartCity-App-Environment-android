package de.osca.android.environment.domain.entity

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("EnvironmentLocale")
class EnvironmentLocale : ParseObject() {
    val key: String by stringAttribute()

    val value: String by stringAttribute()

    val locale: String by stringAttribute()
}
