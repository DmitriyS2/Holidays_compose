package com.sd.holidays.data.dto

data class DataHoliday(
    val date: String,
    val localName: String,
    val name: String,
    val countryCode: String,
    val types: List<String>
)
