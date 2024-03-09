package com.kuliahin.compose.calendarview.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.kuliahin.compose.calendarview.data.CalendarSelection
import com.kuliahin.compose.calendarview.data.CalendarTheme
import com.kuliahin.compose.calendarview.data.CalendarType
import com.kuliahin.compose.calendarview.data.DateTheme
import com.kuliahin.compose.calendarview.data.WeekdaysType
import com.kuliahin.compose.calendarview.vm.CalendarViewModel
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
 * @param onDateClick - day click callback.
 * @param theme - see [CalendarTheme] to customize day view. [CalendarTheme.DEFAULT] by default.
 * @param calendarType - can be [CalendarType.Horizontal.MonthMultiline], [CalendarType.Horizontal.WeekSingleLine] or [com.kuliahin.compose.calendarview.data.CalendarType.MonthMultilineVertical].
 * @param showHeader - set this value to false to hide calendar header with month name.
 * @param userScrollEnabled - set this value to false to prevent user scroll.
 * @param calendarSelection - can be [CalendarSelection.None], [CalendarSelection.Single] or [CalendarSelection.Range].
 * @param onDateRender - callback for conditional date customization. See [DateTheme].
 * @param weekdaysType - day click callback.
 * @param locale - to render weekday labels.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarView(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = viewModel(),
    onDateClick: (LocalDate) -> Unit = {},
    theme: CalendarTheme = CalendarTheme.DEFAULT,
    calendarType: CalendarType = CalendarType.Horizontal.MonthMultiline {},
    showHeader: Boolean = true,
    userScrollEnabled: Boolean = true,
    calendarSelection: CalendarSelection = CalendarSelection.None,
    onDateRender: (@Composable (LocalDate) -> DateTheme?)? = null,
    weekdaysType: WeekdaysType = WeekdaysType.Static,
    locale: Locale = LocalContext.current.resources.configuration.locales[0],
) {
    val datesFlow by remember { mutableStateOf(viewModel.getDatesFlow(calendarType !is CalendarType.Horizontal.WeekSingleLine)) }
    val lazyPagingItems = datesFlow.collectAsLazyPagingItems(Dispatchers.IO)
    val itemWidth = LocalConfiguration.current.screenWidthDp / DayOfWeek.entries.size
    val pagerState = rememberPagerState { lazyPagingItems.itemCount }
    var selectedDates by remember { mutableStateOf(calendarSelection.selectedDates) }
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    val onDateClickCallback: (LocalDate) -> Unit = { clickedDate ->
        when (calendarSelection) {
            is CalendarSelection.Single -> {
                selectedDates =
                    if (selectedDates.contains(clickedDate) && calendarSelection.allowUnselect) {
                        setOf()
                    } else {
                        setOf(clickedDate)
                    }

                calendarSelection.onDateSelected(clickedDate)
            }

            is CalendarSelection.Multiple -> {
                val sortedDates =
                    if (selectedDates.contains(clickedDate)) {
                        (selectedDates - clickedDate)
                    } else {
                        (selectedDates + clickedDate)
                    }.toSortedSet()

                selectedDates = sortedDates

                calendarSelection.onDatesSelected(sortedDates)
            }

            is CalendarSelection.Range -> {
                val sortedDates =
                    if (selectedDates.size == 1) {
                        (selectedDates + clickedDate)
                    } else {
                        setOf(clickedDate)
                    }.toSortedSet()

                selectedDates = sortedDates

                calendarSelection.onRangeSelected(sortedDates)
            }

            else -> Unit
        }

        onDateClick(clickedDate)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            modifier
                .animateContentSize()
                .background(theme.backgroundColor),
    ) {
        if (showHeader) {
            Text(
                currentMonth.month.getDisplayName(
                    TextStyle.FULL_STANDALONE,
                    locale,
                ) + " " + currentMonth.year,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = theme.headerTextColor,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                        .background(theme.headerBackgroundColor),
            )
        }

        if (weekdaysType == WeekdaysType.Static) {
            WeekDaysRow(
                locale = locale,
                itemWidth = itemWidth,
                weekdaysTextColor = theme.weekdaysTextColor,
            )
        }

        val pageContent: @Composable PagerScope.(page: Int) -> Unit = { currentPage ->
            LaunchedEffect(key1 = pagerState.currentPage) {
                lazyPagingItems.takeIf { it.itemCount > 0 }
                    ?.get(pagerState.currentPage)
                    ?.takeIf { it.yearMonth != currentMonth }
                    ?.let { monthDates ->
                        currentMonth = monthDates.yearMonth

                        when (calendarType) {
                            is CalendarType.Horizontal.MonthMultiline ->
                                calendarType.onMonthChanged(
                                    currentMonth,
                                )

                            is CalendarType.MonthMultilineVertical ->
                                calendarType.onMonthChanged(
                                    currentMonth,
                                )

                            is CalendarType.Horizontal.WeekSingleLine ->
                                calendarType.onWeekChanged(
                                    monthDates.dates[0],
                                )
                        }
                    }
            }

            lazyPagingItems[currentPage]?.let { monthDates ->
                CalendarBody(
                    itemWidth = itemWidth,
                    monthDates = monthDates,
                    theme = theme,
                    selectedDates = selectedDates,
                    calendarSelection = calendarSelection,
                    onDateClick = onDateClickCallback,
                    weekdaysType = weekdaysType,
                    locale = locale,
                    onDateRender = onDateRender,
                )
            }
        }

        if (calendarType is CalendarType.Horizontal) {
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = userScrollEnabled,
                key = lazyPagingItems.itemKey { it },
                pageContent = pageContent,
            )
        } else {
            VerticalPager(
                state = pagerState,
                userScrollEnabled = userScrollEnabled,
                key = lazyPagingItems.itemKey { it },
                pageContent = pageContent,
            )
        }
    }
}
