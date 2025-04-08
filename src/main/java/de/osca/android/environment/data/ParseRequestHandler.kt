package de.osca.android.environment.data

import com.parse.ParseException
import de.osca.android.essentials.domain.entity.error.RequestException
import kotlinx.coroutines.coroutineScope

open class ParseRequestHandler {
    suspend fun <T> executeQuery(block: suspend () -> T): T?{
        return coroutineScope {
            try{
                block().run { this }
            } catch (e: ParseException) {
                if (e.code == ParseException.OBJECT_NOT_FOUND) {
                    return@coroutineScope null
                }
                throw when (e.code) {
                    ParseException.TIMEOUT -> RequestException.timeoutError(e)
                    ParseException.OTHER_CAUSE -> RequestException.unexpectedError(e)
                    else -> RequestException.networkError(e)
                }
            }
        }
    }
}
