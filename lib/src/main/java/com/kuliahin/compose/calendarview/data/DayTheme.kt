package com.kuliahin.compose.calendarview.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

/**
 * Class represents [com.kuliahin.compose.calendarview.DayView] properties customization.
 *
 * @param dayBackgroundColor - set needed [Color] to Day representation view background.
 * @param dayValueTextColor - set needed [Color] to Day representation text.
 * @param dayShape - set desired [Shape] to Day representation view.
 */
data class DayTheme(
    val dayBackgroundColor: Color? = null,
    val dayValueTextColor: Color? = null,
    val dayShape: Shape? = null,
)
