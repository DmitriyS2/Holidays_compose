package com.sd.holidays.repository

import android.content.Context
import com.sd.holidays.api.ApiService
import com.sd.holidays.dto.CountryCode
import com.sd.holidays.dto.DataHoliday
import com.sd.holidays.dto.DataLongWeekEnd
import com.sd.holidays.dto.InfoAboutCountry
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService
):Repository {

    private val prefs = context.getSharedPreferences("notification", Context.MODE_PRIVATE)
    private val stateKey = "state"

    override fun getStateNotification(): Boolean = prefs.getBoolean(stateKey, false)

    override fun setStateNotification(state: Boolean) {
        with(prefs.edit()) {
            putBoolean(stateKey, state)
            apply()
        }
    }

    override suspend fun loadDataCountry(): List<CountryCode> {
        try {
            val response = apiService.loadAllCountries()
            if (!response.isSuccessful) {
                return listOf()
            }
            val body = response.body()
            return body ?: listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            return listOf()
        }
    }

    override suspend fun getInfoAboutCountry(code: String): InfoAboutCountry {
        try {
            val response = apiService.getInfoAboutCountry(code)
            if (!response.isSuccessful) {
                return InfoAboutCountry()
            }
            val body = response.body()
            return body ?: InfoAboutCountry()
        } catch (e: Exception) {
            e.printStackTrace()
            return InfoAboutCountry()
        }
    }

    override suspend fun getLongWeekEnd(year: String, code: String): List<DataLongWeekEnd> {
        try {
            val response = apiService.getLongWeekEnd(year, code)
            if (!response.isSuccessful) {
                return listOf()
            }
            val body = response.body()
            return body ?: listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            return listOf()
        }
    }

    override suspend fun getHoliday(year: String, code: String): List<DataHoliday> {
        try {
            val response = apiService.getHoliday(year, code)
            if (!response.isSuccessful) {
                return listOf()
            }
            val body = response.body()
            return body ?: listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            return listOf()
        }
    }

    override suspend fun getNextHoliday(): List<DataHoliday> {
        try {
            val response = apiService.getNextHoliday()
            if (!response.isSuccessful) {
                return listOf()
            }
            val body = response.body()
            return body ?: listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            return listOf()
        }
    }
}