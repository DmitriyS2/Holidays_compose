package com.sd.holidays.model

import com.sd.holidays.dto.InfoAboutCountry

data class ModelInfoAboutCountry(
    val loading: Boolean = false,
    val info: InfoAboutCountry = InfoAboutCountry(),
    val error: Boolean = false
)
