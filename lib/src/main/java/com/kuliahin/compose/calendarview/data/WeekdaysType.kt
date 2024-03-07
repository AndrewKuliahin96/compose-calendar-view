package com.kuliahin.compose.calendarview.data

/**
 * Class represents possible weekday types.
 *
 * [Static] do not scroll weekdays with calendar pages.
 * [Dynamic] scroll weekdays with calendar pages.
 * [None] do not render weekdays.
 */
sealed class WeekdaysType {
    data object Static : WeekdaysType()

    data object Dynamic : WeekdaysType()

    data object None : WeekdaysType()
}
