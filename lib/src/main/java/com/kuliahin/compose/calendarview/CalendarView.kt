package com.kuliahin.compose.calendarview

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.kuliahin.compose.calendarview.component.CalendarFlowRow
import com.kuliahin.compose.calendarview.data.CalendarSelection
import com.kuliahin.compose.calendarview.data.CalendarSwipeDirection
import com.kuliahin.compose.calendarview.data.CalendarTheme
import com.kuliahin.compose.calendarview.data.WeekdaysType
import com.kuliahin.compose.calendarview.data.calendarDefaultTheme
import kotlinx.coroutines.Dispatchers
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarView(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = viewModel(),
    onDayClick: (LocalDate) -> Unit = {},
    theme: CalendarTheme = calendarDefaultTheme,
    expandable: Boolean = false,
    calendarHeight: Dp = 320.dp,
    calendarSwipeDirection: CalendarSwipeDirection = CalendarSwipeDirection.Horizontal,
    calendarSelection: CalendarSelection = CalendarSelection.None,
    onDatesSelected: ((List<LocalDate>) -> Unit)? = null,
    weekdaysType: WeekdaysType = WeekdaysType.Static,
    locale: Locale = LocalContext.current.resources.configuration.locales[0],
) {
    var selectedDates by remember { mutableStateOf(listOf<LocalDate>()) }
    var calendarExpanded by remember { mutableStateOf(true) }

    val lazyPagingItems = viewModel.dates.collectAsLazyPagingItems(Dispatchers.IO)
    val pagerState = rememberPagerState { lazyPagingItems.itemCount }

    val itemWidth = LocalConfiguration.current.screenWidthDp / DayOfWeek.entries.size

    val onDayClickCallback: (LocalDate) -> Unit = { clickedDate ->
        when (calendarSelection) {
            CalendarSelection.Single -> listOf(clickedDate)
            CalendarSelection.Range ->
                if (selectedDates.size == 1) {
                    selectedDates + clickedDate
                } else {
                    listOf(clickedDate)
                }

            else -> null
        }?.let { dates ->
            selectedDates = dates

            onDatesSelected?.invoke(dates)
        }

        onDayClick(clickedDate)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            modifier
                .animateContentSize()
                .background(theme.backgroundColor),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .background(theme.headerBackgroundColor),
        ) {
            Spacer(Modifier.weight(1f))

            val selectedMonth =
                lazyPagingItems.takeIf { it.itemCount > 0 }
                    ?.get(pagerState.currentPage)?.yearMonth
                    ?: YearMonth.now()

            Text(
                selectedMonth.month.getDisplayName(
                    TextStyle.FULL_STANDALONE,
                    locale,
                ) + " " + selectedMonth.year,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = theme.headerTextColor,
            )

            Spacer(Modifier.weight(1f))

            // TODO: Add impl. Currently not working
            if (expandable) {
                IconToggleButton(
                    checked = calendarExpanded,
                    onCheckedChange = { calendarExpanded = it },
                ) {
                    Icon(
                        if (calendarExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        "Toggle button",
                        tint = theme.headerTextColor,
                    )
                }
            }
        }

        if (weekdaysType == WeekdaysType.Static) {
            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                DayOfWeek.entries.forEach { dayOfWeek ->
                    item {
                        Box(
                            Modifier
                                .width(itemWidth.dp)
                                .padding(5.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                DayOfWeek.entries[dayOfWeek.value - 1]
                                    .getDisplayName(TextStyle.SHORT, locale),
                                fontSize = 10.sp,
                                color = theme.weekdaysTextColor,
                            )
                        }
                    }
                }
            }
        }

        if (calendarSwipeDirection == CalendarSwipeDirection.Horizontal) {
            VerticalPager(
                modifier = Modifier.height(calendarHeight),
                state = pagerState,
                key = lazyPagingItems.itemKey { it },
            ) { currentPage ->
                CalendarFlowRow(
                    lazyPagingItems = lazyPagingItems,
                    calendarExpanded = calendarExpanded,
                    calendarHeight = calendarHeight,
                    currentPage = currentPage,
                    theme = theme,
                    selectedDates = selectedDates,
                    onDayClick = onDayClickCallback,
                    weekdaysType = weekdaysType,
                    locale = locale,
                )
            }
        } else {
            HorizontalPager(
                state = pagerState,
                key = lazyPagingItems.itemKey { it },
            ) { currentPage ->
                CalendarFlowRow(
                    lazyPagingItems = lazyPagingItems,
                    calendarExpanded = calendarExpanded,
                    calendarHeight = calendarHeight,
                    currentPage = currentPage,
                    theme = theme,
                    selectedDates = selectedDates,
                    onDayClick = onDayClickCallback,
                    weekdaysType = weekdaysType,
                    locale = locale,
                )
            }
        }
    }
}
