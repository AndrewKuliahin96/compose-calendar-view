package com.kuliahin.compose.calendarview.component

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
import com.kuliahin.compose.calendarview.data.CalendarTheme
import com.kuliahin.compose.calendarview.data.DayTheme
import com.kuliahin.compose.calendarview.data.WeekdaysType
import com.kuliahin.compose.calendarview.paging.MonthDates
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CalendarFlowRow(
    lazyPagingItems: LazyPagingItems<MonthDates>,
    calendarExpanded: Boolean,
    calendarHeight: Dp,
    currentPage: Int,
    theme: CalendarTheme,
    selectedDates: List<LocalDate>,
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
                    val dayViewModifier =
                        Modifier.alpha(
                            if (!calendarExpanded && date.isBefore(LocalDate.now()) ||
                                date.isAfter(currentMonth.atEndOfMonth()) ||
                                (calendarExpanded && date.isBefore(currentMonth.atDay(1)))
                            ) {
                                0.5f
                            } else {
                                1f
                            },
                        )

                    val isSelected =
                        when (selectedDates.size) {
                            1 -> date == selectedDates[0]
                            2 -> {
                                val date1 = selectedDates[0]
                                val date2 = selectedDates[1]

                                val startDate = if (date1.isBefore(date2)) date1 else date2
                                val endDate = if (date1.isBefore(date2)) date2 else date1

                                !date.isBefore(startDate) && !date.isAfter(endDate)
                            }

                            else -> false
                        }

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
