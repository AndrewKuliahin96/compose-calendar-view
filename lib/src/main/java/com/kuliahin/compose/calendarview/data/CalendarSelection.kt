package com.kuliahin.compose.calendarview.data

import java.time.LocalDate

/**
 * Class represents possible types of calendar selection.
 *
 * [None] prevent dates selection.
 * [Single] allow to select only one date.
 * [Range] allow to select range of dates.
 */
sealed class CalendarSelection {
    data object None : CalendarSelection()

    class Single(val onDateSelected: (selectedDate: LocalDate) -> Unit) : CalendarSelection()

    class Range(val onRangeSelected: (selectedDates: List<LocalDate>) -> Unit) : CalendarSelection()
}
