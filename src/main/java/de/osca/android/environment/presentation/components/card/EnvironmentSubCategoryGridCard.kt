package de.osca.android.environment.presentation.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import de.osca.android.environment.domain.entity.EnvironmentSubCategory
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs
import de.osca.android.environment.presentation.components.EnvironmentAsyncImage
import de.osca.android.environment.presentation.environment.viewmodel.locale
import de.osca.android.essentials.presentation.component.design.LocalMasterDesignArgs

@Composable
fun EnvironmentTabGridCard(
    item: EnvironmentSubCategory
) {
    val masterDesignArgs = LocalMasterDesignArgs.current
    val design = LocalEnvironmentDesignArgs.current
    Column(
        modifier = Modifier.padding(design.mCardContentPadding ?: masterDesignArgs.mCardContentPadding),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {
        EnvironmentAsyncImage(url = item.icon.icon.url,
            modifier = Modifier.size(design.envCategoryCardIconSize),
            description = item.icon.definition)
        Text(
            text = locale(item.name),
            fontSize = design.envCardTextFontSize,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            color = design.mCardTextColor ?: masterDesignArgs.mCardTextColor,
            style = masterDesignArgs.normalTextStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
