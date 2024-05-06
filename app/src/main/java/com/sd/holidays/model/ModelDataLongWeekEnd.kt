package com.sd.holidays.model

import com.sd.holidays.dto.DataLongWeekEnd

data class ModelDataLongWeekEnd(
    val loading: Boolean = false,
    val listDataLongWeekEnd: List<DataLongWeekEnd> = emptyList(),
    val error: Boolean = false
)