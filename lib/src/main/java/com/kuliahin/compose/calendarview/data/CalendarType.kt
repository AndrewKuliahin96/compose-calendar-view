package com.kuliahin.compose.calendarview.data

/**
 * Class represents possible types of calendar.
 *
 * [Horizontal] - calendar can scroll only horizontally.
 * Can have month (multiline) [Horizontal.WeekSingleline] or week [Horizontal.WeekSingleline] (single line) representation.
 *
 * [MonthMultilineVertical] - calendar can scroll only horizontally and have month representation.
 */
sealed class CalendarType

sealed class Horizontal : CalendarType() {
    data object MonthMultiline : Horizontal()

    data object WeekSingleline : Horizontal()
}

data object MonthMultilineVertical : CalendarType()
