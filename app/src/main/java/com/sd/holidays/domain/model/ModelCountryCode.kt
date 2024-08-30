package com.sd.holidays.domain.model

data class ModelCountryCode(
    val loading: Boolean = false,
    val listCountry: List<String> = emptyList(),
    val error: Boolean = false
)
