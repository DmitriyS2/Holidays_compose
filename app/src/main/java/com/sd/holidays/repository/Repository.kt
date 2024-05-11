package com.sd.holidays.repository

import com.sd.holidays.dto.CountryCode
import com.sd.holidays.dto.DataHoliday
import com.sd.holidays.dto.DataLongWeekEnd
import com.sd.holidays.dto.InfoAboutCountry

interface Repository {
     suspend fun loadDataCountry(): List<CountryCode>
    suspend fun getInfoAboutCountry(code: String): InfoAboutCountry
    suspend fun getLongWeekEnd(year: String, code: String): List<DataLongWeekEnd>
    suspend fun getHoliday(year: String, code: String): List<DataHoliday>
    suspend fun getNextHoliday(): List<DataHoliday>
}