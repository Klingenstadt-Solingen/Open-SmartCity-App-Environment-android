package de.osca.android.environment.domain.entity

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.intAttribute
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("EnvironmentLanguage")
class EnvironmentLanguage : ParseObject() {
    val name: String by stringAttribute()

    val locale: String by stringAttribute()

    val priority: Int by intAttribute()
}
