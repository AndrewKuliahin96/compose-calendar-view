package com.kuliahin.compose.calendarview.vm

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.kuliahin.compose.calendarview.data.CalendarType
import com.kuliahin.compose.calendarview.paging.DatesPagingSource

/**
 * Class represents vie model for Calendar view.
 */
open class CalendarViewModel : ViewModel() {
    open fun getDatesFlow(calendarType: CalendarType) = Pager(
        config = PagingConfig(initialLoadSize = 2, pageSize = 2, enablePlaceholders = false),
        pagingSourceFactory = { DatesPagingSource(calendarType) },
    ).flow
}
