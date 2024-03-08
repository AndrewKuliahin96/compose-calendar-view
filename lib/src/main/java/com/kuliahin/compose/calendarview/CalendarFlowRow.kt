package com.kuliahin.compose.calendarview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.kuliahin.compose.calendarview.data.CalendarSelection
import com.kuliahin.compose.calendarview.data.CalendarTheme
import com.kuliahin.compose.calendarview.data.CalendarType
import com.kuliahin.compose.calendarview.data.DayTheme
import com.kuliahin.compose.calendarview.data.Horizontal
import com.kuliahin.compose.calendarview.data.WeekdaysType
import com.kuliahin.compose.calendarview.paging.MonthDates
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Locale

/**
 * Class represents single Calendar row.
 *
 * @param lazyPagingItems - paging data with month and dates.
 * @param calendarType - can be [Horizontal.MonthMultiline], [Horizontal.WeekSingleLine] or [com.kuliahin.compose.calendarview.data.MonthMultilineVertical].
 * @param calendarHeight - set height of the Calendar.
 * @param currentPage - provide currentPage field from Pager.
 * @param theme - Calendar customization theme.
 * @param selectedDates - list of dates to select.
 * @param calendarSelection - set of selected dates.
 * @param onDayClick - day click callback.
 * @param weekdaysType - day click callback.
 * @param locale - to render weekday labels.
 * @param onDateRender - callback for conditional [DayView] customization. See [DayTheme].
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CalendarFlowRow(
    lazyPagingItems: LazyPagingItems<MonthDates>,
    calendarType: CalendarType,
    calendarHeight: Dp,
    currentPage: Int,
    theme: CalendarTheme,
    selectedDates: Set<LocalDate>,
    calendarSelection: CalendarSelection,
    onDayClick: (LocalDate) -> Unit,
    weekdaysType: WeekdaysType,
    locale: Locale,
    onDateRender: ((LocalDate) -> DayTheme?)? = null,
) {
    val itemWidth = LocalConfiguration.current.screenWidthDp / DayOfWeek.entries.size

    FlowRow(Modifier.height(calendarHeight)) {
        lazyPagingItems[currentPage]?.let { dates ->
            dates.dates.forEachIndexed { index, date ->
                Box(
                    Modifier
                        .width(itemWidth.dp)
                        .padding(5.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    val currentMonth = dates.yearMonth

                    val isSelected =
                        when (calendarSelection) {
                            is CalendarSelection.None -> false
                            is CalendarSelection.Single,
                            is CalendarSelection.Multiple,
                            -> selectedDates.contains(date)
                            is CalendarSelection.Range ->
                                when (selectedDates.size) {
                                    1 -> selectedDates.contains(date)
                                    2 -> {
                                        val date1 = selectedDates.first()
                                        val date2 = selectedDates.last()

                                        val startDate = if (date1.isBefore(date2)) date1 else date2
                                        val endDate = if (date1.isBefore(date2)) date2 else date1

                                        !date.isBefore(startDate) && !date.isAfter(endDate)
                                    }
                                    else -> false
                                }
                        }

                    val dayViewModifier =
                        Modifier.alpha(
                            if (!isSelected && calendarType is Horizontal.WeekSingleLine && date.isBefore(LocalDate.now()) ||
                                date.isAfter(currentMonth.atEndOfMonth()) ||
                                (calendarType !is Horizontal.WeekSingleLine && date.isBefore(currentMonth.atDay(1)))
                            ) {
                                0.5f
                            } else {
                                1f
                            },
                        )

                    DayView(
                        date,
                        theme = theme,
                        isSelected = isSelected,
                        onDayClick = { onDayClick(date) },
                        weekdayLabel = weekdaysType == WeekdaysType.Dynamic && index < DayOfWeek.entries.size,
                        modifier = dayViewModifier,
                        locale = locale,
                        onDateRender = onDateRender,
                    )
                }
            }
        }
    }
}
