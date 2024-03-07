package com.kuliahin.compose.calendarview.data

/**
 * Class represents possible types of calendar selection.
 *
 * [None] prevent dates selection.
 * [Single] allow to select only one date.
 * [Range] allow to select range of dates.
 */
sealed class CalendarSelection {
    data object None : CalendarSelection()

    data object Single : CalendarSelection()

    data object Range : CalendarSelection()
}
