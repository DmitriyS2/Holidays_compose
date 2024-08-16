package com.sd.holidays.data.api

import com.sd.holidays.data.dto.CountryCode
import com.sd.holidays.data.dto.DataHoliday
import com.sd.holidays.data.dto.InfoAboutCountry
import com.sd.holidays.data.dto.DataLongWeekEnd
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    //list code-country
    @GET("api/v3/AvailableCountries")
    suspend fun loadAllCountries(): Response<List<CountryCode>>

    //инфо о стране по countryCode
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

    //инфо о праздниках в стране по countrycode и году
    @GET("api/v3/PublicHolidays/{year}/{code}")
    suspend fun getHoliday(
        @Path("year") year: String,
        @Path("code") code: String
    ): Response<List<DataHoliday>>

    //инфо о всех праздниках в ближайшие 7 дней
    @GET("api/v3/NextPublicHolidaysWorldwide")
    suspend fun getNextHoliday(): Response<List<DataHoliday>>

}