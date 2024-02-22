package es.iesra.exchange.data

import es.iesra.exchange.domain.CurrencyRateRepository
import es.iesra.exchange.domain.model.Rate
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyRateRepositoryImpl: CurrencyRateRepository {

    private val rateProviderAPI: RateProviderAPI

    init {
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val mOkHttpClient = OkHttpClient.Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()

        rateProviderAPI = retrofit.create(RateProviderAPI::class.java)
    }

    override suspend fun rate(baseCurrency: String, targetCurrency: String): Rate {
        return try {
            val response = rateProviderAPI.rate(baseCurrency,targetCurrency ).awaitResponse()
            if (response.isSuccessful) {
                val rateDTO = response.body() ?: ExchangeRateDTO()
                rateDTO.toModel() // Success
            } else {
                ExchangeRateDTO().toModel() // Error
            }
        } catch (exception: Exception) {
            ExchangeRateDTO().toModel() // Error
        }

    }

    companion object {
        private const val BASE_URL = "https://v6.exchangerate-api.com/v6/2c4d16dd95eaf222822ab33c/"
    }

}