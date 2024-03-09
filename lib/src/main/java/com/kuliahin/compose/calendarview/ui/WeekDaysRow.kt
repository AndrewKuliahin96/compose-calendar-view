package com.kuliahin.compose.calendarview.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun WeekDaysRow(
    locale: Locale,
    itemWidth: Int,
    weekdaysTextColor: Color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
    ) {
        DayOfWeek.entries.forEach { dayOfWeek ->
            Text(
                text =
                    DayOfWeek.entries[dayOfWeek.value - 1]
                        .getDisplayName(TextStyle.SHORT, locale),
                fontSize = 10.sp,
                color = weekdaysTextColor,
                modifier =
                    Modifier
                        .width(itemWidth.dp)
                        .padding(5.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}
