package com.kuliahin.composecalendarview

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kuliahin.compose.calendarview.data.CalendarSelection
import com.kuliahin.compose.calendarview.data.CalendarType
import com.kuliahin.compose.calendarview.data.DateTheme
import com.kuliahin.compose.calendarview.data.WeekdaysType
import com.kuliahin.compose.calendarview.paging.MonthBounds
import com.kuliahin.compose.calendarview.ui.CalendarView
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val rainbowColors =
        listOf(Color.Red, Color.Yellow, Color.Green, Color.Blue, Color.Cyan, Color.Magenta)

    private val selectedDates =
        setOf(
            LocalDate.now().plusDays(4),
            LocalDate.now().plusDays(6),
            LocalDate.now().plusDays(7),
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentMonth = YearMonth.now()

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    CalendarView(
                        modifier = Modifier.fillMaxSize(),
                        weekdaysType = WeekdaysType.Static,
                        calendarType =
                            CalendarType.Horizontal.MonthMultiline { currentMonth ->
                                Log.d("onMonthChanged", currentMonth.toString())
                            },
                        calendarSelection =
                            CalendarSelection.Multiple(selectedDates) { selectedDates ->
                                Log.d("calendarSelection", selectedDates.joinToString())
                            },
                        locale = Locale.US,
                        onDateRender = {
                            DateTheme.DEFAULT.copy(
                                currentMonthDayTheme =
                                    DateTheme.DEFAULT.currentMonthDayTheme.copy(
                                        dayBackgroundColor =
                                            rainbowColors[it.dayOfMonth % rainbowColors.size].copy(
                                                alpha = 0.4F,
                                            ),
                                    ),
                            )
                        },
                        monthBounds =
                            MonthBounds(
                                currentMonth.minusMonths(3),
                                currentMonth.plusMonths(3),
                            ),
                    )
                }
            }
        }
    }
}
