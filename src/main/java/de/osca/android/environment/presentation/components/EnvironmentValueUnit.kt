package de.osca.android.environment.presentation.components;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentViewModel

@Composable
fun EnvironmentValueUnit(value: String, unit: String) {
    val design = LocalEnvironmentDesignArgs.current
    Row(horizontalArrangement = Arrangement.spacedBy(design.envValueUnitRowSpacing)) {
        Text(
            text = value,
            fontSize = design.envValueTextSize,
            color = design.envValueTextColor
        )
        Text(
            modifier = Modifier.padding(top = design.envUnitTopPadding),
            text = unit,
            fontSize = design.envUnitTextSize,
            color = design.envUnitTextColor
        )
    }
}
