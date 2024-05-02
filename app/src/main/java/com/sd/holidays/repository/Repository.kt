package com.sd.holidays.repository

import com.sd.holidays.dto.CountryCode

interface Repository {
     suspend fun loadDataCountry(): List<CountryCode>
}