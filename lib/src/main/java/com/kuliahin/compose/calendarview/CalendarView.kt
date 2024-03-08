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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.kuliahin.compose.calendarview.data.CalendarSelection
import com.kuliahin.compose.calendarview.data.CalendarTheme
import com.kuliahin.compose.calendarview.data.CalendarType
import com.kuliahin.compose.calendarview.data.DayTheme
import com.kuliahin.compose.calendarview.data.Horizontal
import com.kuliahin.compose.calendarview.data.WeekdaysType
import kotlinx.coroutines.Dispatchers
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

/**
 * Class represents Calendar View.
 *
 * @param modifier - customize calendar background.
 * @param viewModel - [CalendarViewModel] or extended class to produce paging of dates.
 * @param onDayClick - day click callback.
 * @param theme - see [CalendarTheme] to customize day view. [CalendarTheme.DEFAULT] by default.
 * @param calendarType - can be [Horizontal.MonthMultiline], [Horizontal.WeekSingleLine] or [com.kuliahin.compose.calendarview.data.MonthMultilineVertical].
 * @param showHeader - set this value to false to hide calendar header with month name.
 * @param calendarHeight - set height of the Calendar.
 * @param calendarSelection - can be [CalendarSelection.None], [CalendarSelection.Single] or [CalendarSelection.Range].
 * @param onMonthChanged - current month callback.
 * @param onDateRender - callback for conditional [DayView] customization. See [DayTheme].
 * @param weekdaysType - day click callback.
 * @param locale - to render weekday labels.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarView(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = viewModel(),
    onDayClick: (LocalDate) -> Unit = {},
    theme: CalendarTheme = CalendarTheme.DEFAULT,
    calendarType: CalendarType = Horizontal.MonthMultiline,
    showHeader: Boolean = true,
    calendarHeight: Dp = 320.dp,
    calendarSelection: CalendarSelection = CalendarSelection.None,
    onMonthChanged: ((YearMonth) -> Unit)? = null,
    onDateRender: ((LocalDate) -> DayTheme?)? = null,
    weekdaysType: WeekdaysType = WeekdaysType.Static,
    locale: Locale = LocalContext.current.resources.configuration.locales[0],
) {
    val lazyPagingItems = viewModel.dates.collectAsLazyPagingItems(Dispatchers.IO)
    val pagerState = rememberPagerState { lazyPagingItems.itemCount }
    val itemWidth = LocalConfiguration.current.screenWidthDp / DayOfWeek.entries.size
    var selectedDates by remember { mutableStateOf(listOf<LocalDate>()) }
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    val onDayClickCallback: (LocalDate) -> Unit = { clickedDate ->
        when (calendarSelection) {
            is CalendarSelection.Single -> {
                calendarSelection.onDateSelected(clickedDate)

                selectedDates = listOf(clickedDate)
            }
            is CalendarSelection.Range -> {
                val dates = if (selectedDates.size == 1) {
                    (selectedDates + clickedDate).sortedBy { it.dayOfYear }
                } else {
                    listOf(clickedDate)
                }

                calendarSelection.onRangeSelected(dates)

                selectedDates = dates
            }

            else -> Unit
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
        if (showHeader) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                        .background(theme.headerBackgroundColor),
            ) {
                Spacer(Modifier.weight(1f))

                Text(
                    currentMonth.month.getDisplayName(
                        TextStyle.FULL_STANDALONE,
                        locale,
                    ) + " " + currentMonth.year,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = theme.headerTextColor,
                )

                Spacer(Modifier.weight(1f))
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

        if (calendarType is Horizontal) {
            HorizontalPager(
                state = pagerState,
                key = lazyPagingItems.itemKey { it },
            ) { currentPage ->
                LaunchedEffect(key1 = pagerState.currentPage) {
                    lazyPagingItems.takeIf { it.itemCount > 0 }
                        ?.get(pagerState.currentPage)?.yearMonth?.takeIf { it != currentMonth }?.let {
                            currentMonth = it

                            onMonthChanged?.invoke(currentMonth)
                        }
                }

                CalendarFlowRow(
                    lazyPagingItems = lazyPagingItems,
                    calendarType = calendarType,
                    calendarHeight = calendarHeight,
                    currentPage = currentPage,
                    theme = theme,
                    selectedDates = selectedDates,
                    onDayClick = onDayClickCallback,
                    weekdaysType = weekdaysType,
                    locale = locale,
                    onDateRender = onDateRender,
                )
            }
        } else {
            VerticalPager(
                modifier = Modifier.height(calendarHeight),
                state = pagerState,
                key = lazyPagingItems.itemKey { it },
            ) { currentPage ->
                LaunchedEffect(key1 = pagerState.currentPage) {
                    lazyPagingItems.takeIf { it.itemCount > 0 }
                        ?.get(pagerState.currentPage)?.yearMonth?.takeIf { it != currentMonth }?.let {
                            currentMonth = it

                            onMonthChanged?.invoke(currentMonth)
                        }
                }

                CalendarFlowRow(
                    lazyPagingItems = lazyPagingItems,
                    calendarType = calendarType,
                    calendarHeight = calendarHeight,
                    currentPage = currentPage,
                    theme = theme,
                    selectedDates = selectedDates,
                    onDayClick = onDayClickCallback,
                    weekdaysType = weekdaysType,
                    locale = locale,
                    onDateRender = onDateRender,
                )
            }
        }
    }
}
