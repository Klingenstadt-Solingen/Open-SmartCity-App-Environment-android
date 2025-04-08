package de.osca.android.environment.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs

@Composable
fun EnvironmentSubtitle(text: String) {
    val design = LocalEnvironmentDesignArgs.current
    Spacer(Modifier.size(design.envSubtitleTopSpacerSize))
    Row() {
        Text(
            text,
            modifier = Modifier.padding(PaddingValues(start = design.envSubtitleTextStartPadding))
                .fillMaxWidth(),
            textAlign = TextAlign.Left
        )
    }
    Spacer(Modifier.size(design.envSubtitleBottomSpacerSize))
}
