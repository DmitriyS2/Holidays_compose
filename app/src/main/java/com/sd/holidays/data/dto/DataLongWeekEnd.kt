package com.sd.holidays.data.dto

import java.time.LocalDate

data class DataLongWeekEnd(
    val startDate: String, //"2024-05-06T07:42:39.450Z",
    val endDate: String, //"2024-05-06T07:42:39.450Z",
    val date: LocalDate,
    val dayCount: Int //0,
)
