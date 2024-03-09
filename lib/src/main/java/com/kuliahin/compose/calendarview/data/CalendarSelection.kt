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
sealed class CalendarSelection(val selectedDates: Set<LocalDate> = setOf()) {
    data object None : CalendarSelection()

    class Single(
        selectedDate: LocalDate? = null,
        val allowUnselect: Boolean = true,
        val onDateSelected: (selectedDate: LocalDate) -> Unit = {},
    ) : CalendarSelection(selectedDate?.let(::setOf)?.toSortedSet() ?: setOf())

    class Multiple(
        selectedDates: Set<LocalDate> = setOf(),
        val onDatesSelected: (
            selectedDates: SortedSet<LocalDate>,
        ) -> Unit = {},
    ) : CalendarSelection(selectedDates.toSortedSet())

    class Range(
        selectedDates: Set<LocalDate> = setOf(),
        val onRangeSelected: (selectedDates: SortedSet<LocalDate>) -> Unit = {},
    ) : CalendarSelection(selectedDates.toSortedSet())
}
