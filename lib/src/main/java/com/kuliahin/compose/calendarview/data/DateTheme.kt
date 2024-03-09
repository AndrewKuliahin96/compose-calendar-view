package com.kuliahin.compose.calendarview.data

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

/**
 * Class represents properties for date customization.
 *
 * @param todayBackgroundColor - set today date representation view background color.
 * @param selectedTodayBackgroundColor - set today selected date representation view background color.
 * @param dateBackgroundColor - set date representation view background color.
 * @param selectedDateBackgroundColor - set selected date representation view background color.
 * @param dateValueTextColor - set date representation text color.
 * @param selectedDateValueTextColor - set selected date representation text color.
 * @param dateShape - set desired date representation view [Shape].
 */
data class DateTheme(
    val todayBackgroundColor: Color,
    val selectedTodayBackgroundColor: Color,
    val dateBackgroundColor: Color,
    val selectedDateBackgroundColor: Color,
    val dateValueTextColor: Color,
    val selectedDateValueTextColor: Color,
    val dateShape: Shape,
) {
    companion object {
        /**
         * Default day view customization.
         */
        val DEFAULT: DateTheme
            @Composable
            @ReadOnlyComposable
            get() =
                DateTheme(
                    todayBackgroundColor = MaterialTheme.colorScheme.secondary,
                    selectedTodayBackgroundColor = MaterialTheme.colorScheme.onSecondary,
                    dateBackgroundColor = Color.Transparent,
                    selectedDateBackgroundColor = MaterialTheme.colorScheme.primary,
                    dateValueTextColor = MaterialTheme.colorScheme.onBackground,
                    selectedDateValueTextColor = MaterialTheme.colorScheme.onBackground,
                    dateShape = CircleShape,
                )
    }
}
