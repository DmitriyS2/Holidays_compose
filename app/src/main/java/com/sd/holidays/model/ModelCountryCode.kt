package com.sd.holidays.model

import com.sd.holidays.dto.CountryCode

data class ModelCountryCode(
    val loading:Boolean = false,
    val listCountry:List<String> = emptyList(),
    val error: Boolean = false
)
