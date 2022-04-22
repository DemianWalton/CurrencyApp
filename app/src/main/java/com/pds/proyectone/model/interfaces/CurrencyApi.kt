package com.pds.proyectone.model.interfaces

import com.pds.proyectone.model.data.CryptocurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyApi {

    @GET("assets/{asset_id}")
    suspend fun getCryptoInfo(
        @Path("asset_id") assetId: String,
        @Query("apikey") accessKey: String
    ): Response<List<CryptocurrencyResponse>>
}