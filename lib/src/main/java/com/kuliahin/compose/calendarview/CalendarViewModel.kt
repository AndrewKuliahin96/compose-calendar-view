package com.kuliahin.compose.calendarview

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.kuliahin.compose.calendarview.paging.DatesPagingSource

// TODO: Make it open?
class CalendarViewModel : ViewModel() {
    val dates by lazy {
        Pager(
            config = PagingConfig(pageSize = 3, enablePlaceholders = false),
            pagingSourceFactory = { DatesPagingSource() },
        ).flow
    }
}
