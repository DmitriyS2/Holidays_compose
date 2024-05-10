package com.sd.holidays.model

import com.sd.holidays.dto.DataHoliday

data class ModelDataHoliday(
    val loading: Boolean = false,
    val listDataHoliday: List<DataHoliday> = emptyList(),
    val error: Boolean = false
)