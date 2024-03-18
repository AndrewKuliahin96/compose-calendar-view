package com.kuliahin.compose.calendarview.data

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

/**
 * Class represents properties for date customization.
 *
 * @param todayTheme - customize current day background/text colors and shape.
 * @param currentMonthDayTheme - customize current month days background/text colors and shape.
 * @param otherMonthDayTheme - customize other month days background/text colors and shape.
 */
data class DateTheme(
    val todayTheme: DayTheme,
    val currentMonthDayTheme: DayTheme,
    val otherMonthDayTheme: DayTheme,
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
                    todayTheme =
                        DayTheme(
                            dayBackgroundColor = MaterialTheme.colorScheme.primary,
                            daySelectedBackgroundColor = MaterialTheme.colorScheme.secondary,
                            dayDisabledBackgroundColor = MaterialTheme.colorScheme.inversePrimary,
                            dayTextColor = MaterialTheme.colorScheme.onPrimary,
                            daySelectedTextColor = MaterialTheme.colorScheme.onSecondary,
                        ),
                    currentMonthDayTheme =
                        DayTheme(
                            dayBackgroundColor = Color.Transparent,
                            daySelectedBackgroundColor = MaterialTheme.colorScheme.primary,
                            dayDisabledBackgroundColor = MaterialTheme.colorScheme.background,
                            dayTextColor = MaterialTheme.colorScheme.onBackground,
                            daySelectedTextColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                    otherMonthDayTheme =
                        DayTheme(
                            dayBackgroundColor = Color.Transparent,
                            daySelectedBackgroundColor = MaterialTheme.colorScheme.primary,
                            dayDisabledBackgroundColor = MaterialTheme.colorScheme.background,
                            dayTextColor = MaterialTheme.colorScheme.onBackground,
                            daySelectedTextColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                )
    }
}
