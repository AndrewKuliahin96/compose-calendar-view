package com.kuliahin.composecalendarview

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kuliahin.compose.calendarview.CalendarView
import com.kuliahin.compose.calendarview.data.CalendarSelection
import com.kuliahin.compose.calendarview.data.DayTheme
import com.kuliahin.compose.calendarview.data.WeekdaysType
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val rainbowColors = listOf(Color.Red, Color.Yellow, Color.Green, Color.Blue, Color.Cyan, Color.Magenta)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    CalendarView(
                        modifier = Modifier.fillMaxSize(),
                        weekdaysType = WeekdaysType.Static,
                        calendarSelection = CalendarSelection.Range,
                        locale = Locale.US,
                        onDateRender = {
                            val selectedColor = rainbowColors[it.dayOfMonth % rainbowColors.size]

                            val valueTextColor =
                                if (selectedColor == Color.Red || selectedColor == Color.Magenta) {
                                    Color.Blue
                                } else {
                                    Color.Black
                                }

                            DayTheme(
                                selectedColor.copy(alpha = 0.4F),
                                valueTextColor,
                                RoundedCornerShape(50 - it.dayOfMonth),
                            )
                        },
                        onMonthChanged = { currentMonth ->
                            Log.e("onMonthChanged", currentMonth.toString())
                        },
                        onDatesSelected = { dates ->
                            Log.e("onDatesSelected", dates.joinToString())
                        },
                    )
                }
            }
        }
    }
}
