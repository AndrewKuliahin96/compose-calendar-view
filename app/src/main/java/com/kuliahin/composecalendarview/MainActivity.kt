package com.kuliahin.composecalendarview

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kuliahin.compose.calendarview.CalendarView
import com.kuliahin.compose.calendarview.data.CalendarSelection
import com.kuliahin.compose.calendarview.data.WeekdaysType
import java.util.*

class MainActivity : AppCompatActivity() {
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
                        locale = Locale.US
                    )
                }
            }
        }
    }
}
