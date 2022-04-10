package com.pds.proyectone.model.interfaces

import com.pds.proyectone.model.data.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("v1/latest")
    suspend fun getLatestRates(
        @Query("currencyBase") currencyBase: String,
        @Query("accessKey") accessKey: String
    ): Response<CurrencyResponse>


}