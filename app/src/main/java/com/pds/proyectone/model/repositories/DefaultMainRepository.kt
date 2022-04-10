package com.pds.proyectone.model.repositories

import com.pds.proyectone.model.data.CurrencyResponse
import com.pds.proyectone.model.interfaces.CurrencyApi
import com.pds.proyectone.util.DataState
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: CurrencyApi
) : MainRepositoryInterface {

    override suspend fun getRates(currencyBase: String, accessKey: String): DataState<CurrencyResponse> {
        return try {
            val response = api.getLatestRates(currencyBase,accessKey)
            val result = response.body()
            if(response.isSuccessful && result != null )
                DataState.Success(result) else DataState.Failure(response.message())

        } catch (e: Exception) {
            DataState.Failure(e.message ?: "Ocurrio un problema")
        }
    }
}