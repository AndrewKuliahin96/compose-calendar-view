package com.kuliahin.compose.calendarview.data

import java.time.LocalDate
import java.time.YearMonth

/**
 * Class represents possible types of calendar.
 *
 * [Horizontal] - calendar can scroll only horizontally.
 * Can have month (multiline) [Horizontal.WeekSingleLine] or week [Horizontal.WeekSingleLine] (single line) representation.
 *
 * [MonthMultilineVertical] - calendar can scroll only horizontally and have month representation.
 */
sealed class CalendarType

sealed class Horizontal : CalendarType() {
    class MonthMultiline(val onMonthChanged: (currentMonth: YearMonth) -> Unit) : Horizontal()

    class WeekSingleLine(val onWeekChanged: (weekStartDay: LocalDate) -> Unit) : Horizontal()
}

class MonthMultilineVertical(val onMonthChanged: (currentMonth: YearMonth) -> Unit) : CalendarType()
