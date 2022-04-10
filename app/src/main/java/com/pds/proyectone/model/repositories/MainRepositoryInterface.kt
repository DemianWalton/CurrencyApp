package com.pds.proyectone.model.repositories

import com.pds.proyectone.model.data.CurrencyResponse
import com.pds.proyectone.util.DataState

interface MainRepositoryInterface {

    suspend fun getRates(base: String, accessKey: String): DataState<CurrencyResponse>

}