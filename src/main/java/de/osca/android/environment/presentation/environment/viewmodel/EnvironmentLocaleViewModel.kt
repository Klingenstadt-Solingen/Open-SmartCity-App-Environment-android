package de.osca.android.environment.presentation.environment.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.environment.domain.EnvironmentLanguageRepository
import de.osca.android.environment.domain.EnvironmentLocaleRepository
import de.osca.android.essentials.presentation.base.BaseViewModel
import de.osca.android.essentials.utils.extensions.displayContent
import de.osca.android.essentials.utils.extensions.loading
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import javax.inject.Inject

@HiltViewModel
class EnvironmentLocaleViewModel
    @Inject
    constructor(
        private val localeRepository: EnvironmentLocaleRepository,
        private val languageRepository: EnvironmentLanguageRepository,
    ) : BaseViewModel() {
        var locales: Map<String, String> = mutableStateMapOf()
        var language: String = Locale.current.language.lowercase()

        fun initFetch() {
            wrapperState.loading()
            killChildren()
            locales = mutableStateMapOf()
            fetchLocales(language)
        }

        fun killChildren() {
            viewModelScope.coroutineContext.cancelChildren()
        }

        fun fetchLocales(locale: String): Job =
            launchDataLoad {
                val checkLanguage = languageRepository.checkLanguage(locale)
                val fetchLanguage =
                    if (!checkLanguage) {
                        languageRepository.getAlternativeLanguage(Locale.current.language)
                            ?: "de-DE"
                    } else {
                        locale
                    }
                val queryResult = localeRepository.getLocales(fetchLanguage)
                queryResult.forEach {
                    locales = locales.plus(Pair(it.key, it.value))
                }
                language = fetchLanguage
                wrapperState.displayContent()
            }

        fun locale(key: String): String {
            val keyResult = locales[key]

            if (keyResult == null) {
                fetchLocale(key)
            }

            return keyResult ?: key
        }

        private fun fetchLocale(key: String): Job =
            launchDataLoad {
                localeRepository.getLocaleByKey(key, language)?.let {
                    locales.plus(Pair(key, it.value))
                }
            }
    }

val LocalEnvironmentLocale =
    compositionLocalOf<EnvironmentLocaleViewModel> { error("No CompositionLocal for LocalEnvironmentLocale") }

@Composable
@ReadOnlyComposable
fun locale(key: String): String {
    return LocalEnvironmentLocale.current.locale(key)
}
