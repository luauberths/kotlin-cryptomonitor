package carreiras.com.github.cryptomonitor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import carreiras.com.github.cryptomonitor.model.Quote
import carreiras.com.github.cryptomonitor.service.CryptoService
import carreiras.com.github.cryptomonitor.state.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CryptoViewModel(
    private val service: CryptoService
) : ViewModel() {

    private val _tickerLiveData = MutableLiveData<ScreenState>()
    private val _quotesLiveData = MutableLiveData<List<Quote>>()

    val tickerLiveData: LiveData<ScreenState> = _tickerLiveData
    val quotesLiveData: LiveData<List<Quote>> = _quotesLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetch()
            generateQuotes()
        }
    }

    private fun generateQuotes() {
        val quotes = listOf(
            Quote("Cotação Bitcoin", 314_593.40, "06/09/2024 09:47"),
            Quote("Cotação Bitcoin", 315_470.40, "06/09/2024 09:48"),
            Quote("Cotação Bitcoin", 315_408.91, "06/09/2024 09:49"),
            Quote("Cotação Bitcoin", 315_505.72, "06/09/2024 09:50"),
            Quote("Cotação Bitcoin", 315_221.38, "06/09/2024 09:50"),
            Quote("Cotação Bitcoin", 315_942.38, "06/09/2024 09:51"),
            Quote("Cotação Bitcoin", 316_365.34, "06/09/2024 09:53"),
            Quote("Cotação Bitcoin", 317_330.45, "06/09/2024 09:54"),
            Quote("Cotação Bitcoin", 316_152.36, "06/09/2024 09:54"),
            Quote("Cotação Bitcoin", 316_152.36, "06/09/2024 09:55"),
            Quote("Cotação Bitcoin", 317_455.04, "06/09/2024 09:56"),
        )

        _quotesLiveData.postValue(quotes)
    }

    private suspend fun fetch() {
        _tickerLiveData.postValue(ScreenState.Loading)

        try {
            val response = service.fetchCoinTicker()
            if (response.isSuccessful) {
                val tickerResponse = response.body()
                if (tickerResponse != null) {
                    _tickerLiveData.postValue(
                        ScreenState.Success(
                            data = tickerResponse.ticker
                        )
                    )
                }
            }
        } catch (exception: Throwable) {
            _tickerLiveData.postValue(
                ScreenState.Error(
                    exception
                )
            )
        }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            fetch()
        }
    }
}