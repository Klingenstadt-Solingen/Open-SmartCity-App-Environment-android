package de.osca.android.environment.presentation.args

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import de.osca.android.essentials.presentation.component.design.ModuleDesignArgs
import de.osca.android.essentials.presentation.component.design.WidgetDesignArgs

interface EnvironmentDesignArgs : ModuleDesignArgs, WidgetDesignArgs {
    /** default */
    val envCardTextFontSize: TextUnit
    val envCardIconSize: Dp
    val envDefaultElevation: Dp

    /** Value Unit*/
    val envValueUnitRowSpacing: Dp
    val envValueTextSize: TextUnit
    val envValueTextColor: Color
    val envUnitTopPadding: Dp
    val envUnitTextSize: TextUnit
    val envUnitTextColor: Color

    /** TABS */
    val envTabTextFontSize: TextUnit
    val envTabShadowStroke: Dp
    val envTabPadding: Dp
    val envTabTextColor: Color
    val envTabSelectedColor: Color
    val envTabUnselectedColor: Color
    val envTabShadowColor: Color
    val envTabBackgroundColor: Color
    val envTabShape: Shape
    val envTabRowBottomPadding: Dp
    val envTabBackgroundShadowElevation: Dp
    val envTabTextFontWeight: FontWeight

    /** Category Card*/
    val envCategoryCardIconSize: Dp
    val envCategoryCardIconColor: Color

    /** Measurement Card */
    val envMeasureCardBack: Color
    val envMeasureListItemShape: Shape
    val envMeasureTabColor: Color
    val envMeasureTabShape: Shape
    val envMeasureCardIconSize: Dp
    val envMeasureCardIconColor: Color
    val envMeasureCardInfoIconSize:Dp
    val envMeasureCardInfoIconColor: Color
    val envMeasureCardTitleTextSize: TextUnit
    val envMeasureCardTitleTextColor: Color

    /** Location Tab */
    val envLocationTabTextSize: TextUnit
    val envLocationTabTextFontWeight: FontWeight
    val envLocationTabTextColor: Color
    val envLocationTabColorSelected: Color
    val envLocationTabColorUnselected: Color
    val envLocationTabShape: Shape

    /** Backbutton */
    val envBackButtonRippleRadius: Dp
    val envBackButtonSpacing: Dp
    val envBackButtonIcon: Int
    val envBackButtonIconHeight: Dp
    val envBackButtonIconWidth: Dp
    val envBackButtonIconColor: Color
    val envBackButtonTextSize: TextUnit
    val envBackButtonTextColor: Color

    /** Subtitle */
    val envSubtitleTopSpacerSize: Dp
    val envSubtitleTextStartPadding: Dp
    val envSubtitleBottomSpacerSize: Dp

    /** Measurement Detail */
    val envMeasurementDetailSpacerSizeSmall: Dp
    val envMeasurementDetailSpacerSizeMedium: Dp
    val envMeasurementDetailSpacerSizeBig: Dp
    val envMeasurementDetailRowSpacing: Dp
    val envMeasurementDetailIconSize: Dp
    val envMeasurementDetailIconColor: Color
    val envMeasurementDetailCardColor: Color
    val envMeasurementDetailTextColor: Color
    val envMeasurementDetailTextFontWeight: FontWeight
    val envMeasurementDetailGraphLineColor: Color
    val envMeasurementDetailGraphGradientFirstColor: Color
    val envMeasurementDetailGraphGradientSecondColor: Color
    val envMeasurementDetailGraphPadding: Dp
    val envMeasurementDetailGraphStroke: Dp
    val envMeasurementDetailGraphCoordinateLine: Dp

    /** Measurement Location */
    val envMeasurementLocationSpacerSmall: Dp
    val envMeasurementLocationSpacerBig: Dp
    val envMeasurementLocationTabBackgroundColor: Color
    val envMeasurementLocationTabPadding: Dp
    val envMeasurementLocationButtonColor: Color
    val envMeasurementLocationButtonElevation: Dp
    val envMeasurementLocationRowSpacing: Dp
    val envMeasurementLocationIconSize: Dp
    val envMeasurementLocationIcon: Int
    val envMeasurementLocationIconColor: Color
    val envMeasurementLocationTextSize: TextUnit
}

val LocalEnvironmentDesignArgs = compositionLocalOf<EnvironmentDesignArgs> { error("No CompositionLocal for LocalEnvironmentDesignArgs")}
