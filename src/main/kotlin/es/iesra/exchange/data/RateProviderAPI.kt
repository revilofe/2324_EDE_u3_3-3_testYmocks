package es.iesra.exchange.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RateProviderAPI {
    @GET("pair/{baseCurrency}/{targetCurrency}")
    fun rate(@Path("baseCurrency") baseCurrency: String, @Path("targetCurrency") targetCurrency: String): Call<ExchangeRateDTO>
}

