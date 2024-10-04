package carreiras.com.github.cryptomonitor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import carreiras.com.github.cryptomonitor.service.CryptoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoViewModelFactory : ViewModelProvider.Factory {

    private fun createService(): CryptoService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.mercadobitcoin.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: CryptoService = retrofit.create(CryptoService::class.java)
        return service
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CryptoViewModel(service = createService()) as T
    }
}