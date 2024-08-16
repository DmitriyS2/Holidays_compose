package com.sd.holidays.domain.model

import com.sd.holidays.data.dto.DataLongWeekEnd

data class ModelDataLongWeekEnd(
    val loading: Boolean = false,
    val listDataLongWeekEnd: List<DataLongWeekEnd> = emptyList(),
    val error: Boolean = false
)