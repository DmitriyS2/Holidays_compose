package com.sd.holidays.api

import com.sd.holidays.dto.CountryCode
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    //list code-country
    @GET("api/v3/AvailableCountries")
    suspend fun loadAllCountries(): Response<List<CountryCode>>


}