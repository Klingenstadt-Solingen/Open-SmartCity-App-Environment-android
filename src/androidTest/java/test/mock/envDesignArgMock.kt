package test.mock

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.osca.android.environment.presentation.args.EnvironmentDesignArgs
import de.osca.android.environment.R

fun envDesignArgMock(): EnvironmentDesignArgs {
    return object : EnvironmentDesignArgs {
        override val envMeasurementDetailGraphCoordinateLine: Dp = 3.dp
        override val envMeasurementDetailGraphStroke: Dp = 3.dp
        override val envMeasurementDetailGraphPadding: Dp = 10.dp
        override val envMeasurementDetailGraphGradientSecondColor: Color = Color.Gray
        override val envMeasurementDetailGraphGradientFirstColor: Color = Color.Blue
        override val envMeasurementDetailGraphLineColor: Color = Color.Blue
        override val envDefaultElevation: Dp = 5.dp
        override val envCardTextFontSize: TextUnit = 18.sp
        override val envCardIconSize: Dp = 70.dp
        override val envMeasureCardBack: Color = Color(242, 242, 242)
        override val envMeasureListItemShape: Shape = RoundedCornerShape(0.dp).copy(
            topStart = CornerSize(10.dp),
            topEnd = CornerSize(10.dp),
            bottomStart = CornerSize(1.dp),
            bottomEnd = CornerSize(1.dp)
        )
        override val envValueUnitRowSpacing: Dp = 4.dp
        override val envValueTextSize: TextUnit = 30.sp
        override val envValueTextColor: Color = Color(47, 100, 177)
        override val envUnitTopPadding: Dp = 4.dp
        override val envUnitTextSize: TextUnit = 20.sp
        override val envUnitTextColor: Color = Color(47, 100, 177)
        override val envTabTextFontSize: TextUnit = 14.sp
        override val envTabShadowStroke: Dp = 2.dp
        override val envTabPadding: Dp = 10.dp
        override val envTabTextColor: Color = Color.Black
        override val envTabSelectedColor: Color = Color.White
        override val envTabUnselectedColor: Color = Color(242, 242, 242)
        override val envTabShadowColor: Color = Color(242, 242, 242)
        override val envTabBackgroundColor: Color = Color.White
        override val envTabShape: Shape = RoundedCornerShape(0.dp).copy(
            topStart = CornerSize(10.dp),
            topEnd = CornerSize(10.dp),
            bottomStart = CornerSize(1.dp),
            bottomEnd = CornerSize(1.dp)
        )
        override val envTabRowBottomPadding: Dp = 8.dp
        override val envTabBackgroundShadowElevation: Dp = 4.dp
        override val envTabTextFontWeight: FontWeight = FontWeight.Bold
        override val envCategoryCardIconSize: Dp = 70.dp
        override val envCategoryCardIconColor: Color = Color.White
        override val envMeasureTabColor: Color = Color(242, 242, 242)
        override val envMeasureTabShape: Shape = RoundedCornerShape(10.dp)
        override val envMeasureCardIconSize: Dp = 35.dp
        override val envMeasureCardIconColor: Color = Color(47, 100, 177)
        override val envMeasureCardInfoIconSize: Dp = 20.dp
        override val envMeasureCardInfoIconColor: Color = Color(125, 125, 125)
        override val envMeasureCardTitleTextSize: TextUnit = 15.sp
        override val envMeasureCardTitleTextColor: Color = Color(0, 67, 114)
        override val envLocationTabTextSize: TextUnit = 20.sp
        override val envLocationTabTextFontWeight: FontWeight = FontWeight.Bold
        override val envLocationTabTextColor: Color = Color(0, 67, 115)
        override val envLocationTabColorSelected: Color = Color(255, 191, 0)
        override val envLocationTabColorUnselected: Color = envMeasureTabColor
        override val envLocationTabShape: Shape = envMeasureTabShape
        override val envBackButtonRippleRadius: Dp = 24.dp
        override val envBackButtonSpacing: Dp = 10.dp
        override val envBackButtonIcon: Int = R.drawable.ic_left
        override val envBackButtonIconHeight: Dp = 30.dp
        override val envBackButtonIconWidth: Dp = 12.dp
        override val envBackButtonIconColor: Color = Color(255, 191, 0)
        override val envBackButtonTextSize: TextUnit = 35.sp
        override val envBackButtonTextColor: Color = Color(0, 67, 115)
        override val envSubtitleTopSpacerSize: Dp = 20.dp
        override val envSubtitleTextStartPadding: Dp = 22.dp
        override val envSubtitleBottomSpacerSize: Dp = 10.dp
        override val envMeasurementDetailSpacerSizeSmall: Dp = 5.dp
        override val envMeasurementDetailSpacerSizeMedium: Dp = 10.dp
        override val envMeasurementDetailSpacerSizeBig: Dp = 16.dp
        override val envMeasurementDetailRowSpacing: Dp = 15.dp
        override val envMeasurementDetailIconSize: Dp = 35.dp
        override val envMeasurementDetailIconColor: Color = Color(47, 100, 177)
        override val envMeasurementDetailCardColor: Color = Color.White
        override val envMeasurementDetailTextColor: Color = Color(47, 100, 177)
        override val envMeasurementDetailTextFontWeight: FontWeight = FontWeight.Bold
        override val envMeasurementLocationSpacerSmall: Dp = 16.dp
        override val envMeasurementLocationSpacerBig: Dp = 20.dp
        override val envMeasurementLocationTabBackgroundColor: Color = Color.White
        override val envMeasurementLocationTabPadding: Dp = 5.dp
        override val envMeasurementLocationButtonColor: Color = Color(242, 242, 242)
        override val envMeasurementLocationButtonElevation: Dp = 0.dp
        override val envMeasurementLocationRowSpacing: Dp = 4.dp
        override val envMeasurementLocationIconSize: Dp = 8.dp
        override val envMeasurementLocationIcon: Int = R.drawable.ic_right
        override val envMeasurementLocationIconColor: Color = Color(255, 191, 0)
        override val envMeasurementLocationTextSize: TextUnit = 18.sp
        override val vShowMoreOption: Boolean = true
        override val vIsWidgetVisible: Boolean = true
        override val vModuleTitle: Int = 0
        override val vWidgetShowMoreOption: Boolean = true
        override val vWidgetTitle: Int = 0
        override val mTopBarBackColor: Color = Color.White
        override val mBottomBarBackColor: Color? = null
        override val mStatusBarBackColor: Color? = null
        override val mCardBackColor: Color = Color(25, 56, 179)
        override val mMenuBackColor: Color? = null
        override val mSheetBackColor: Color? = null
        override val mScreenBackColor: Color? = null
        override val mTopBarTextColor: Color = Color.Blue
        override val mBottomBarTextColor: Color? = null
        override val mStatusBarTextColor: Color? = null
        override val mCardTextColor: Color = Color.White
        override val mMenuTextColor: Color? = null
        override val mSheetTextColor: Color? = null
        override val mScreenTextColor: Color? = null
        override val mHintTextColor: Color? = null
        override val mTextInCard: Boolean? = null
        override val mRootCardSpacing: Dp? = null
        override val mRootBoarderSpacing: Dp? = null
        override val mCardContentPadding: Dp = 16.dp
        override val mConstrainHeight: Dp? = null
        override val mCardElevation: Dp? = null
        override val mSheetElevation: Dp? = null
        override val mShowLessText: Int? = null
        override val mShowMoreText: Int? = null
        override val mHeaderTextColor: Color? = null
        override val mShowMoreTextColor: Color? = null
        override val mShapeCard: Dp? = null
        override val mShapeBottomSheet: Shape? = null
        override val mShapeTopSheet: Shape? = null
        override val mShapeSheet: Shape? = null
        override val mButtonBackgroundColor: Color? = null
        override val mButtonContentColor: Color? = null
        override val mSwitchCheckedThumbColor: Color? = null
        override val mSwitchCheckedTrackColor: Color? = null
        override val mSwitchUncheckedThumbColor: Color? = null
        override val mSwitchUncheckedTrackColor: Color? = null
        override val mDropDownBorderColor: Color? = null
        override val mTextFieldFocusedBorderColor: Color? = null
        override val mTextFieldUnfocusedBorderColor: Color? = null
        override val mDialogsBackgroundColor: Color? = null
        override val mDialogsTextColor: Color? = null
        override val mDialogsBackColor: Color? = null
        override val mBorderSpace: Dp? = null
        override val mContentPaddingForMiniCards: Dp? = null
        override val mRoundIconSize: Dp? = null
        override val mWidgetShowLessText: Int? = null
        override val mWidgetShowMoreText: Int? = null
        override val mWidgetHeaderTextColor: Color? = null
        override val mWidgetShowMoreTextColor: Color? = null
        override val mIsStatusBarWhite: Boolean? = null
    }

}
