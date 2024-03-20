package com.kuliahin.compose.calendarview.data

import java.time.LocalDate
import java.time.YearMonth

/**
 * Class represents possible bounds for calendar dates.
 *
 * [DateBounds] - represents bounds for two dates and using in week calendar.
 *
 * [MonthBounds] - bounds for month calendar type to scroll between months.
 */
abstract class Bounds

open class DateBounds(
    open val startDate: LocalDate? = null,
    open val endDate: LocalDate? = null
): Bounds()

open class MonthBounds(
    open val startMonth: YearMonth? = null,
    open val endMonth: YearMonth? = null
) : Bounds()
