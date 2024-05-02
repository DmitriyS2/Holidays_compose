package com.sd.holidays.dto

data class InfoAboutCountry(
    val commonName: String,
    val officialName: String,
    val countryCode: String,
    val region: String,
    val borders: List<InfoAboutCountry>?
)
