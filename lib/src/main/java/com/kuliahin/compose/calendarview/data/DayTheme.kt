package com.kuliahin.compose.calendarview.data

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

/**
 * Class represents properties for day customization.
 *
 * @param dayBackgroundColor - day background color.
 * @param daySelectedBackgroundColor - selected day background color.
 * @param dayDisabledBackgroundColor - disabled day background color.
 * @param dayTextColor - day text color.
 * @param daySelectedTextColor - day selected text color.
 * @param dayShape - day shape.
 */
data class DayTheme(
    val dayBackgroundColor: Color,
    val daySelectedBackgroundColor: Color,
    val dayDisabledBackgroundColor: Color,
    val dayTextColor: Color,
    val daySelectedTextColor: Color,
    val dayShape: Shape = CircleShape,
)
