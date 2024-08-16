package com.sd.holidays.domain

import com.sd.holidays.data.dto.CountryCode
import com.sd.holidays.data.dto.DataHoliday
import com.sd.holidays.data.dto.DataLongWeekEnd
import com.sd.holidays.data.dto.InfoAboutCountry

interface Repository {

    fun getStateNotification(): Boolean
    fun setStateNotification(state: Boolean)
     suspend fun loadDataCountry(): List<CountryCode>
    suspend fun getInfoAboutCountry(code: String): InfoAboutCountry
    suspend fun getLongWeekEnd(year: String, code: String): List<DataLongWeekEnd>
    suspend fun getHoliday(year: String, code: String): List<DataHoliday>
    suspend fun getNextHoliday(): List<DataHoliday>
}