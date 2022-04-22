package com.pds.proyectone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pds.proyectone.model.repositories.DefaultMainRepository
import com.pds.proyectone.util.DataState
import com.pds.proyectone.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.MathContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DefaultMainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class CurrencyEvent {
        class Success(val resultText: String) : CurrencyEvent()
        class Failure(val failureText: String) : CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion


    fun convert(
        currencyId: String,
        amountToConvert: String
    ) {
        if (currencyId.isEmpty() || amountToConvert.isEmpty()) {
            _conversion.value = CurrencyEvent.Failure("Both fields must be filled in")
            return
        }

        val amount = amountToConvert.toFloatOrNull()
        if (amount == null) {
            _conversion.value = CurrencyEvent.Failure("You must specify a valid amount")
            return
        }

        viewModelScope.launch(dispatchers.io) {
            _conversion.value = CurrencyEvent.Loading
            when (val currencyResponse =
                repository.getCrypto(currencyId)) {
                is DataState.Failure -> _conversion.value =
                    CurrencyEvent.Failure(currencyResponse.message!!)
                is DataState.Success -> {
                    val response = currencyResponse.data!!
                    val currencyValue = (amount / response[0].price_usd).toBigDecimal().round(
                        MathContext(4)
                    )
                    _conversion.value = CurrencyEvent.Success(
                        "1 $currencyId " +
                                "${response[0].price_usd}" +
                                " =\n" +
                                " USD $amountToConvert  " +
                                "$currencyValue"
                    )
                }
            }
        }
    }

}

