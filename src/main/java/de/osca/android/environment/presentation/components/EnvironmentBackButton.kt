package de.osca.android.environment.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.osca.android.environment.presentation.environment.viewmodel.EnvironmentViewModel
import de.osca.android.environment.R
import de.osca.android.environment.presentation.args.LocalEnvironmentDesignArgs

@Composable
fun BackButton(
    navController: NavController,
    text: String? = null
) {
    val design = LocalEnvironmentDesignArgs.current
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .clickable(
                role = Role.Button
            ) {
                navController.popBackStack()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(design.envBackButtonSpacing)
    ) {
        Icon(
            painter = painterResource(design.envBackButtonIcon),
            contentDescription = "Back Icon",
            modifier = Modifier
                .height(design.envBackButtonIconHeight)
                .width(design.envBackButtonIconWidth),
            tint = design.envBackButtonIconColor
        )
        Text(
            text = text ?: stringResource(R.string.back_button),
            fontSize = design.envBackButtonTextSize,
            color = design.envBackButtonTextColor
        )
    }
}
