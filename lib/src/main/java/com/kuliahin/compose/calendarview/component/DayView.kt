package com.kuliahin.compose.calendarview.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuliahin.compose.calendarview.data.CalendarTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

/**
 * View that represent one day in the calendar
 * @param date date that view should represent
 * @param weekdayLabel flag that indicates if name of week day should be visible above day value
 * @param modifier view modifier
 */
@Composable
fun DayView(
    date: LocalDate,
    onDayClick: (LocalDate) -> Unit,
    theme: CalendarTheme,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    weekdayLabel: Boolean = true,
    locale: Locale = LocalContext.current.resources.configuration.locales[0],
) {
    val isCurrentDay = date == LocalDate.now()

    val backgroundColor =
        when {
            isCurrentDay -> theme.selectedDayBackgroundColor.copy(alpha = 0.5f)
            isSelected -> theme.selectedDayBackgroundColor
            else -> theme.dayBackgroundColor
        }

    val dayValueModifier = modifier.background(backgroundColor, shape = theme.dayShape)
    val maxHeight = if (weekdayLabel) 50 + 20 else 50

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier =
            Modifier
                .heightIn(max = maxHeight.dp)
                .widthIn(max = 50.dp),
    ) {
        if (weekdayLabel) {
            Text(
                DayOfWeek.entries[date.dayOfWeek.value - 1].getDisplayName(TextStyle.SHORT, locale),
                fontSize = 10.sp,
                color = theme.weekdaysTextColor,
            )
        }

        Box(
            dayValueModifier
                .padding(5.dp)
                .aspectRatio(1f)
                .clickable { onDayClick(date) },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                date.dayOfMonth.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = if (isSelected || isCurrentDay) theme.selectedDayValueTextColor else theme.dayValueTextColor,
            )
        }
    }
}
