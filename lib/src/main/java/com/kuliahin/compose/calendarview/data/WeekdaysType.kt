package com.kuliahin.compose.calendarview.data

sealed class WeekdaysType {
    data object Static : WeekdaysType()

    data object Dynamic : WeekdaysType()

    data object None : WeekdaysType()
}
