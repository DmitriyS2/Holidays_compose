package com.sd.holidays.domain.model

import com.sd.holidays.data.dto.InfoAboutCountry

data class ModelInfoAboutCountry(
    val loading: Boolean = false,
    val info: InfoAboutCountry = InfoAboutCountry(),
    val error: Boolean = false
)
