package com.kuliahin.compose.calendarview.paging

import android.os.Parcelable
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kuliahin.compose.calendarview.data.Bounds
import com.kuliahin.compose.calendarview.data.CalendarType
import com.kuliahin.compose.calendarview.data.DateBounds
import com.kuliahin.compose.calendarview.data.MonthBounds
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

/**
 * PagingSource for generating Calendar view dates.
 */
open class DatesPagingSource(private val calendarType: CalendarType) : PagingSource<DatesKey, MonthDates>() {
    override fun getRefreshKey(state: PagingState<DatesKey, MonthDates>): DatesKey? = null

    override suspend fun load(params: LoadParams<DatesKey>): LoadResult<DatesKey, MonthDates> {
        try {
            val key =
                params.key ?: DatesKey(YearMonth.now(), LocalDate.now().with(DayOfWeek.MONDAY))

            val isMonthView = calendarType !is CalendarType.Horizontal.WeekSingleLine

            val monthDates =
                if (isMonthView) {
                    val startOfMonth = key.yearMonth.atDay(1)
                    val prevMonthDates = startOfMonth.dayOfWeek.value - 1
                    val start = startOfMonth.minusDays(prevMonthDates.toLong())
                    val dates = (0 until 42).map { start.plusDays(it.toLong()) }

                    MonthDates(key.yearMonth, dates)
                } else {
                    val dates = (0 until 7).map { key.startOfWeekDate.plusDays(it.toLong()) }

                    val yearMonth =
                        dates.groupBy { YearMonth.from(it) }.maxByOrNull { it.value.size }?.key
                            ?: key.yearMonth

                    MonthDates(yearMonth, dates)
                }

            val isValid = isInBounds(key,calendarType.bounds )

            return LoadResult.Page(
                data = if (isValid) listOf(monthDates) else emptyList(),
                prevKey =
                    if (isValid) {
                        DatesKey(
                            key.yearMonth.minusMonths(1),
                            key.startOfWeekDate.minusWeeks(1),
                        )
                    } else {
                        null
                    },
                nextKey =
                    if (isValid) {
                        DatesKey(
                            key.yearMonth.plusMonths(1),
                            key.startOfWeekDate.plusWeeks(1),
                        )
                    } else {
                        null
                    },
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    open fun isInBounds(key: DatesKey, bounds: Bounds): Boolean {
       return when(bounds) {
            is MonthBounds -> key.yearMonth >= bounds.startMonth && key.yearMonth <= bounds.endMonth
            is DateBounds -> key.startOfWeekDate >= bounds.startDate && key.startOfWeekDate <= bounds.endDate
            else -> true
        }
    }
}

/**
 * Data for Calendar view paging.
 *
 * @param yearMonth - represents month page.
 * @param startOfWeekDate - list of [LocalDate] dates for calendar view.
 */
@Parcelize
data class DatesKey(val yearMonth: YearMonth, val startOfWeekDate: LocalDate) : Parcelable

/**
 * Data for Calendar view paging.
 *
 * @param yearMonth - represents month page.
 * @param dates - list of [LocalDate] dates for calendar view.
 */
@Parcelize
data class MonthDates(val yearMonth: YearMonth, val dates: List<LocalDate>) : Parcelable
