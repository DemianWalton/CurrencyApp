package com.pds.proyectone.model.repositories

import com.pds.proyectone.BuildConfig
import com.pds.proyectone.model.data.CryptocurrencyResponse
import com.pds.proyectone.model.interfaces.CurrencyApi
import com.pds.proyectone.util.DataState
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: CurrencyApi
) : MainRepositoryInterface {

    override suspend fun getCrypto(cryptoId: String): DataState<List<CryptocurrencyResponse>> {
        return try {
            val response = api.getCryptoInfo(cryptoId, BuildConfig.API_KEY)
            val result = response.body()
            if (response.isSuccessful && !result.isNullOrEmpty())
                DataState.Success(result) else DataState.Failure(
                response.message().ifEmpty { "Your search has not returned any results" })
        } catch (e: Exception) {
            DataState.Failure(e.message ?: "Error in API communication")
        }
    }
}