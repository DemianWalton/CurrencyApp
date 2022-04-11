package com.pds.proyectone.model.repositories

import com.pds.proyectone.model.data.CurrencyResponse
import com.pds.proyectone.util.DataState

interface MainRepositoryInterface {

    suspend fun getRates(currencyBase: String): DataState<CurrencyResponse>

}