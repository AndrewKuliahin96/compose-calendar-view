package com.kuliahin.compose.calendarview.data

sealed class CalendarSelection {
    data object None : CalendarSelection()

    data object Single : CalendarSelection()

    data object Range : CalendarSelection()
}
