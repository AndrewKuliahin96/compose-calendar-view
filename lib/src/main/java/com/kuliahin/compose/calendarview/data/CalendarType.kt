package com.kuliahin.compose.calendarview.data

import java.time.LocalDate
import java.time.YearMonth

/**
 * Class represents possible types of calendar.
 *
 * [Horizontal] - calendar can scroll only horizontally.
 * Can have month (multiline) [Horizontal.MonthMultiline] or week [Horizontal.WeekSingleLine] (single line) representation.
 *
 * [VerticalMonthMultiline] - calendar can scroll only vertically and have month representation.
 */
sealed class CalendarType(open val bounds: Bounds) {
    sealed class Horizontal(override val bounds: Bounds) : CalendarType(bounds) {
        class MonthMultiline(
            bounds: MonthBounds = MonthBounds(),
            val onMonthChanged: (currentMonth: YearMonth) -> Unit
        ) : Horizontal(bounds)

        class WeekSingleLine(
            bounds: DateBounds = DateBounds(),
            val onWeekChanged: (weekStartDay: LocalDate) -> Unit
        ) : Horizontal(bounds)
    }

    class VerticalMonthMultiline(
        monthBounds: MonthBounds = MonthBounds(),
        val onMonthChanged: (currentMonth: YearMonth) -> Unit
    ) : CalendarType(monthBounds)
}
