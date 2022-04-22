package com.pds.proyectone.model.repositories

import com.pds.proyectone.model.data.CryptocurrencyResponse
import com.pds.proyectone.util.DataState

interface MainRepositoryInterface {

    suspend fun getCrypto(cryptoId: String): DataState<List<CryptocurrencyResponse>>

}