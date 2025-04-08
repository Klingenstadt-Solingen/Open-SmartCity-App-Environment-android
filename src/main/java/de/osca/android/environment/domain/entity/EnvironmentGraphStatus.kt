package de.osca.android.environment.domain.entity

sealed class EnvironmentGraphStatus {
    object Loading: EnvironmentGraphStatus()
    class Loaded(val data: List<EnvironmentSensorValue>): EnvironmentGraphStatus()
    class Error(val error: String): EnvironmentGraphStatus()
}
