package de.osca.android.environment.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.essentials.presentation.component.design.LocalMasterDesignArgs

@Composable
fun EnvironmentMeasurementTab(
    text: String,
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    val design = LocalEnvironmentDesignArgs.current
    val masterDesignArgs = LocalMasterDesignArgs.current
    Tab(
        text = {
            Text(
                text = text,
                fontSize = design.envLocationTabTextSize,
                style = masterDesignArgs.normalTextStyle,
                fontWeight = design.envLocationTabTextFontWeight,
                color = design.envLocationTabTextColor
            )
        },
        selected = selected,
        onClick = {
            onClick()
        },
        modifier = modifier.fillMaxWidth()
            .background(
                color = if (selected) design.envLocationTabColorSelected
                    else design.envLocationTabColorUnselected,
                shape = design.envLocationTabShape
            )
    )
}
