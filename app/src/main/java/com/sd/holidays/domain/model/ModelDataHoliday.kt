package com.sd.holidays.domain.model

import com.sd.holidays.data.dto.DataHoliday

data class ModelDataHoliday(
    val loading: Boolean = false,
    val listDataHoliday: List<DataHoliday> = emptyList(),
    val error: Boolean = false
)