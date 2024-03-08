package com.kuliahin.compose.calendarview.data

import java.time.LocalDate
import java.util.SortedSet

/**
 * Class represents possible types of calendar selection.
 *
 * [None] prevent dates selection.
 * [Single] allow to select only one date.
 * [Range] allow to select range of dates.
 */
sealed class CalendarSelection {
    data object None : CalendarSelection()

    class Single(
        val allowUnselect: Boolean = true,
        val onDateSelected: (selectedDate: LocalDate) -> Unit = {},
    ) : CalendarSelection()

    class Multiple(val onDatesSelected: (selectedDates: SortedSet<LocalDate>) -> Unit = {}) :
        CalendarSelection()

    class Range(val onRangeSelected: (selectedDates: SortedSet<LocalDate>) -> Unit = {}) :
        CalendarSelection()
}
