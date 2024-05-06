package com.sd.holidays.api

import com.sd.holidays.dto.CountryCode
import com.sd.holidays.dto.InfoAboutCountry
import com.sd.holidays.dto.DataLongWeekEnd
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    //list code-country
    @GET("api/v3/AvailableCountries")
    suspend fun loadAllCountries(): Response<List<CountryCode>>

    //инфо о стране по countrycode
    @GET("api/v3/CountryInfo/{code}")
    suspend fun getInfoAboutCountry(
        @Path("code") code: String
    ): Response<InfoAboutCountry>

    //инфо о длинных выходных в стране по countrycode и году
    @GET("api/v3/LongWeekend/{year}/{code}")
    suspend fun getLongWeekEnd(
        @Path("year") year: String,
        @Path("code") code: String
    ): Response<List<DataLongWeekEnd>>
}