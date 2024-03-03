package com.kuliahin.compose.calendarview.data

sealed class CalendarSwipeDirection {
    data object Vertical : CalendarSwipeDirection()

    data object Horizontal : CalendarSwipeDirection()
}
