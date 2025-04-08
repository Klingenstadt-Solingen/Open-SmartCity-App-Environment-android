package test.mock

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.osca.android.essentials.presentation.component.design.MasterDesignArgs

fun masterDesignArgsMock(): MasterDesignArgs {
    return object : MasterDesignArgs {
        /**
         * these are the values that must be set
         * this is used by the module and all screens with the same viewModel
         */
        override val reallyBigTextStyle = Typography.h1
        override val bigTextStyle = Typography.h2
        override val captionTextStyle = Typography.caption
        override val overlineTextStyle = Typography.overline
        override val buttonTextStyle = Typography.button
        override val bodyTextStyle = Typography.body1
        override val normalTextStyle = Typography.body2
        override val subtitleTextStyle = Typography.subtitle1
        override val subtitleBoldTextStyle = Typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        override val highlightColor: Color = CLR_Accent
        override val appSpecificColor: Color = CLR_City
        override val errorTextColor = CLR_ErrorText
        override val buttonDefaultElevation = 6.dp
        override val buttonPressedElevation = 8.dp
        override val buttonDisabledElevation = 0.dp
        override val datePickerDialogHeaderBackgroundColor = CLR_BackgroundDialog
        override val datePickerDialogHeaderTextColor = CLR_TextDialog
        override val datePickerDialogDateActiveBackgroundColor = CLR_BackgroundDialog
        override val datePickerDialogDateActiveTextColor = CLR_TextDialog
        override val timePickerDialogInactiveBackgroundColor = CLR_BackgroundMain.copy(0.1f)
        override val timePickerDialogActiveBackgroundColor = CLR_BackgroundDialog
        override val timePickerDialogActiveTextColor = CLR_TextDialog

        override val timePickerDialogSelectorColor = CLR_BackgroundDialog
        override val timePickerDialogSelectorTextColor = CLR_TextDialog


        override val weatherGradientColorTop = CLR_GradientTop
        override val weatherGradientColorBottom = CLR_GradientBottom
        override val spaceDefault = 16.dp
        override val spaceCards = 16.dp
        override val spaceList = 16.dp
        override val mContentPaddingForMiniCards = 8.dp
        override val mRoundIconSize = 100.dp
        override val mBorderSpace = 16.dp
        override val lightPrimary = CLR_GradientBottom
        override val dividerColor = CLR_TextHint
        /**
         * the overrides can be overwritten by each module self
         * must be set
         */
        override val mCardBackColor = CLR_BackgroundCard
        override val mMenuBackColor = CLR_BackgroundDialog
        override val mSheetBackColor = CLR_BackgroundSheet
        override val mScreenBackColor = CLR_BackgroundMain
        override val mTopBarTextColor = CLR_TextSheet
        override val mBottomBarTextColor = CLR_TextSheet
        override val mStatusBarTextColor = CLR_TextSheet
        override val mShapeCard = 10.dp
        override val mShapeSheet = RoundedCornerShape(10.dp)
        override val mShapeTopSheet = RoundedCornerShape(0.dp).copy(
            bottomStart = CornerSize(10.dp),
            bottomEnd = CornerSize(10.dp)
        )
        override val mShapeBottomSheet: Shape = RoundedCornerShape(0.dp).copy(
            topStart = CornerSize(10.dp),
            topEnd = CornerSize(10.dp)
        )
        override val mCardTextColor = CLR_TextCard
        override val mMenuTextColor = CLR_TextDialog
        override val mSheetTextColor = CLR_TextSheet
        override val mScreenTextColor = CLR_TextScreen
        override val mHintTextColor = CLR_TextHint
        override val mTextInCard = true
        override val mRootCardSpacing = 16.dp
        override val mRootBoarderSpacing = 16.dp
        override val mCardContentPadding = 16.dp
        override val mConstrainHeight = 0.dp
        override val mCardElevation = 8.dp
        override val mSheetElevation = 8.dp
        override val mShowLessText = 0
        override val mShowMoreText = 0
        override val mHeaderTextColor = CLR_TextNormal
        override val mShowMoreTextColor = CLR_ShowMore
        override val vShowMoreOption = true
        override val vModuleTitle = 0
        override val mSwitchCheckedThumbColor = CLR_BackgroundDialog
        override val mSwitchCheckedTrackColor = CLR_BackgroundDialog.copy(alpha = 0.7f)
        override val mSwitchUncheckedThumbColor = CLR_TextHint
        override val mSwitchUncheckedTrackColor = CLR_TextHint.copy(alpha = 0.7f)
        override val mButtonBackgroundColor = CLR_BackgroundButton
        override val mButtonContentColor = CLR_TextButton
        override val mDialogsBackColor = CLR_BackgroundDialog
        override val mDialogsTextColor = CLR_TextDialog
        override val mWidgetShowLessText = 0
        override val mWidgetShowMoreText = 0
        override val mWidgetHeaderTextColor = CLR_TextNormal
        override val mWidgetShowMoreTextColor = CLR_ShowMore
        override val vWidgetShowMoreOption = true
        override val vIsWidgetVisible = true
        override val vWidgetTitle = 0
        override val mTopBarBackColor = CLR_BackgroundSheet
        override val mBottomBarBackColor = CLR_BackgroundSheet
        override val mStatusBarBackColor = CLR_BackgroundSheet
        override val mDropDownBorderColor = CLR_TextHint
        override val mTextFieldFocusedBorderColor = CLR_Accent
        override val mTextFieldUnfocusedBorderColor = CLR_TextHint
        override val mTextFieldLabelColor = CLR_TextHint
        override val mIsStatusBarWhite = false
    }
}
