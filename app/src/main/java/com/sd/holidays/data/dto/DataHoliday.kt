package com.sd.holidays.data.dto

import java.time.LocalDate

data class DataHoliday(
    val date: String,
    val dateLocalDate: LocalDate,
    val localName: String,
    val name: String,
    val countryCode: String,
    val types: List<String>
)
