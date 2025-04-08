package de.osca.android.environment.presentation.environment.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import de.osca.android.environment.R
import de.osca.android.environment.domain.boundaries.EnvironmentCategoryRepository
import de.osca.android.environment.domain.boundaries.EnvironmentSensorRepository
import de.osca.android.environment.domain.boundaries.EnvironmentStorage
import de.osca.android.environment.domain.entity.EnvironmentCategory
import de.osca.android.environment.domain.entity.EnvironmentSensor
import de.osca.android.environment.presentation.args.EnvironmentDesignArgs
import de.osca.android.essentials.presentation.base.BaseViewModel
import de.osca.android.essentials.utils.extensions.displayContent
import de.osca.android.essentials.utils.extensions.failure
import de.osca.android.essentials.utils.extensions.loading
import de.osca.android.essentials.utils.extensions.resetWith
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class EnvironmentViewModel
    @Inject
    constructor(
        val environmentDesignArgs: EnvironmentDesignArgs,
        private val categoryRepository: EnvironmentCategoryRepository,
        private val sensorRepository: EnvironmentSensorRepository,
        private val environmentStorage: EnvironmentStorage,
        @ApplicationContext private val context: Context,
    ) : BaseViewModel() {
        val categories = mutableStateListOf<EnvironmentCategory>()
        val activatedSensors = mutableStateListOf<EnvironmentSensor>()

        fun activateSensor(sensor: EnvironmentSensor) =
            launchDataLoad {
                // add to activatedSensors
                activatedSensors.add(sensor)

                // save the list with sensor data in permanent memory
                environmentStorage.saveActivatedSensors(activatedSensors.map { it.objectId })
            }

        fun deactivateSensor(sensorId: String) =
            launchDataLoad {
                // delete from activatedSensors
                activatedSensors.removeIf { it.objectId == sensorId }
                // save the updated list with sensor data in permanent memory
                environmentStorage.saveActivatedSensors(activatedSensors.map { it.objectId })
            }

        fun loadActivatedSensors() =
            launchDataLoad {
                // load the activatedSensors list from permanent memory
                val sensorObjectIds = environmentStorage.loadActivatedSensors().firstOrNull()
                // load fresh data from sensors
                sensorObjectIds?.let { fetchSensors(it) }
            }

        fun initFetch() {
            wrapperState.loading()
            fetchCategories()
            loadActivatedSensors()
        }

        private fun fetchCategories(): Job =
            launchDataLoad {
                categories.resetWith(categoryRepository.getCategories())
                if (categories.isEmpty()) {
                    wrapperState.failure(context.resources.getString(R.string.environment_no_result))
                } else {
                    wrapperState.displayContent()
                }
            }

        private fun fetchSensors(list: List<String>): Job =
            launchDataLoad {
                activatedSensors.resetWith(sensorRepository.getSensorsByTheirId(list))
            }
    }
