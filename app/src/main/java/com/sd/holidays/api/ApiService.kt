package com.sd.holidays.api

import com.sd.holidays.dto.CountryCode
import com.sd.holidays.dto.InfoAboutCountry
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    //list code-country
    @GET("api/v3/AvailableCountries")
    suspend fun loadAllCountries(): Response<List<CountryCode>>

    //инфо о стране по countrycode
    @GET("api/v3/CountryInfo/{code}")
    suspend fun getInfoAboutCountry(@Path("code") code: String): Response<InfoAboutCountry>
}