package com.kuliahin.compose.calendarview.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuliahin.compose.calendarview.data.CalendarSelection
import com.kuliahin.compose.calendarview.data.CalendarTheme
import com.kuliahin.compose.calendarview.data.DateTheme
import com.kuliahin.compose.calendarview.data.WeekdaysType
import com.kuliahin.compose.calendarview.paging.MonthDates
import java.time.LocalDate
import java.util.Locale

@Composable
fun CalendarBody(
    itemWidth: Int,
    monthDates: MonthDates,
    theme: CalendarTheme,
    selectedDates: Set<LocalDate>,
    calendarSelection: CalendarSelection,
    onDateClick: (LocalDate) -> Unit,
    weekdaysType: WeekdaysType,
    locale: Locale,
    onDateRender: (@Composable (LocalDate) -> DateTheme?)? = null,
) {
    Column(modifier = Modifier.fillMaxHeight()) {
        if (weekdaysType == WeekdaysType.Dynamic) {
            WeekDaysRow(
                locale = locale,
                itemWidth = itemWidth,
                weekdaysTextColor = theme.weekdaysTextColor,
            )
        }

        monthDates.dates.chunked(7).forEach { week ->
            Row(modifier = Modifier.fillMaxWidth()) {
                week.forEach { date ->
                    val isSelected =
                        when (calendarSelection) {
                            is CalendarSelection.None -> false
                            is CalendarSelection.Single,
                            is CalendarSelection.Multiple,
                            -> selectedDates.contains(date)

                            is CalendarSelection.Range ->
                                when (selectedDates.size) {
                                    1 -> selectedDates.contains(date)
                                    2 ->
                                        !date.isBefore(selectedDates.first()) &&
                                            !date.isAfter(selectedDates.last())
                                    else -> false
                                }
                        }

                    val dateTheme = onDateRender?.invoke(date) ?: theme.dateTheme
                    val isToday = date == LocalDate.now()
                    val isCurrentMonth = date.month != monthDates.yearMonth.month

                    val dayTheme =
                        when {
                            isToday -> dateTheme.todayTheme
                            isCurrentMonth -> dateTheme.currentMonthDayTheme
                            else -> dateTheme.otherMonthDayTheme
                        }

                    val backgroundColor =
                        when {
                            isToday -> dayTheme.dayBackgroundColor
                            isSelected -> dayTheme.daySelectedBackgroundColor
                            else -> dayTheme.dayDisabledBackgroundColor
                        }

                    Box(
                        modifier =
                            Modifier
                                .alpha(if (isCurrentMonth) 0.5f else 1f)
                                .background(backgroundColor, shape = dayTheme.dayShape)
                                .size(itemWidth.dp)
                                .padding(5.dp)
                                .clip(dayTheme.dayShape)
                                .clickable { onDateClick(date) },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = date.dayOfMonth.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            color = if (isSelected) dayTheme.daySelectedTextColor else dayTheme.dayTextColor,
                        )
                    }
                }
            }
        }
    }
}
