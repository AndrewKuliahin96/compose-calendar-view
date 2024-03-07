package com.kuliahin.compose.calendarview

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.kuliahin.compose.calendarview.paging.DatesPagingSource

/**
 * Class represents vie model for Calendar view.
 */
open class CalendarViewModel : ViewModel() {
    open val dates by lazy {
        Pager(
            config = PagingConfig(pageSize = 3, enablePlaceholders = false),
            pagingSourceFactory = { DatesPagingSource() },
        ).flow
    }
}
