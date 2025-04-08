package de.osca.android.environment.domain.entity

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseRelation
import com.parse.ktx.delegates.relationAttribute
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("EnvironmentCategory")
class EnvironmentCategory : ParseObject() {
    val name: String by stringAttribute()

    val subCategories: ParseRelation<EnvironmentSubCategory> by relationAttribute()

    val order: String by stringAttribute()

    val color: String by stringAttribute()
}
