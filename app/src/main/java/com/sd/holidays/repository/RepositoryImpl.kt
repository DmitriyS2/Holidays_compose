package com.sd.holidays.repository

import com.sd.holidays.api.ApiService
import com.sd.holidays.dto.CountryCode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
):Repository {
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
}