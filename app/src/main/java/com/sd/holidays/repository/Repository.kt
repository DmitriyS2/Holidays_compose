package com.sd.holidays.repository

import com.sd.holidays.dto.CountryCode
import com.sd.holidays.dto.InfoAboutCountry

interface Repository {
     suspend fun loadDataCountry(): List<CountryCode>
    suspend fun getInfoAboutCountry(code: String): InfoAboutCountry
}