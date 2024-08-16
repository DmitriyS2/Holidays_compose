package com.sd.holidays.data.dto

data class InfoAboutCountry(
    val commonName: String = "",
    val officialName: String = "",
    val countryCode: String = "",
    val region: String = "",
    val borders: List<InfoAboutCountry>? = null
)
