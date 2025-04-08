package de.osca.android.environment.domain.entity

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ktx.delegates.attribute
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("EnvironmentIcon")
class EnvironmentIcon : ParseObject() {
    val icon: ParseFile by attribute()

    val definition: String by stringAttribute()
}
