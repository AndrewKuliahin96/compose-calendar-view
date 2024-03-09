package com.kuliahin.compose.calendarview.data

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

/**
 * Class represents possible calendar view customization.
 */
data class CalendarTheme(
    val backgroundColor: Color,
    val headerBackgroundColor: Color,
    val headerTextColor: Color,
    val weekdaysTextColor: Color,
    val dateTheme: DateTheme,
) {
    companion object {
        /**
         * Default calendar view customization.
         */
        val DEFAULT: CalendarTheme
            @Composable
            @ReadOnlyComposable
            get() =
                CalendarTheme(
                    backgroundColor = Color.Transparent,
                    headerBackgroundColor = Color.Transparent,
                    headerTextColor = MaterialTheme.colorScheme.onBackground,
                    weekdaysTextColor = MaterialTheme.colorScheme.onBackground,
                    dateTheme = DateTheme.DEFAULT,
                )
    }
}
