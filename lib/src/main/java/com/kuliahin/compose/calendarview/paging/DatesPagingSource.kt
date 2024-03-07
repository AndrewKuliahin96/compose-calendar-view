package com.kuliahin.compose.calendarview.paging

import android.os.Parcelable
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.YearMonth
import kotlin.math.max

/**
 * PagingSource for generating Calendar view dates.
 */
internal class DatesPagingSource : PagingSource<YearMonth, MonthDates>() {
    override fun getRefreshKey(state: PagingState<YearMonth, MonthDates>): YearMonth? {
        return null
    }

    override suspend fun load(params: LoadParams<YearMonth>): LoadResult<YearMonth, MonthDates> {
        try {
            val key = params.key ?: YearMonth.now()
            val dates = mutableListOf<LocalDate>()
            val startOfMonth = key.atDay(1)
            val prevMonthDays = startOfMonth.dayOfWeek.value - 1

            for (i in 1..prevMonthDays) {
                dates.add(startOfMonth.minusDays(i.toLong()))
            }

            dates.reverse()

            val lengthOfMonth = key.lengthOfMonth()

            for (i in 1..lengthOfMonth) {
                dates.add(LocalDate.of(key.year, key.month, i))
            }

            val remainingDays = max(42 - dates.size, 0)
            val nextMonthStart = key.plusMonths(1).atDay(1)

            for (i in 0 until remainingDays) {
                dates.add(nextMonthStart.plusDays(i.toLong()))
            }

            return LoadResult.Page(
                data = listOf(MonthDates(key, dates)),
                prevKey = key.minusMonths(1),
                nextKey = key.plusMonths(1),
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}

/**
 * Data for Calendar view paging.
 *
 * @param yearMonth - represents month page.
 * @param dates - list of [LocalDate] dates for calendar view.
 */
@Parcelize
data class MonthDates(val yearMonth: YearMonth, val dates: List<LocalDate>) : Parcelable
