package de.osca.android.environment.domain.entity.delegate

import com.parse.ParseObject
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.reflect.KProperty


class DateParseDelegate(private val name: String?) {
    operator fun getValue(parseObject: ParseObject, property: KProperty<*>): Date? {
        val date = parseObject.getDate(name ?: property.name)
        if (date != null) {
            return date
        }
        return null
    }

    operator fun setValue(parseObject: ParseObject, property: KProperty<*>, value: Date) {
        parseObject.put(name ?: property.name, value)
    }
}

inline fun dateAttribute(name: String? = null) = DateParseDelegate(name)
