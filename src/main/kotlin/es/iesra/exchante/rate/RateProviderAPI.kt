package es.iesra.exchante.rate

// https://medium.com/@jeremy.leyvraz/kotlin-simplify-your-api-calls-with-elegance-with-retrofit-1be6da7adae4
// https://app.exchangerate-api.com/keys
// key 2c4d16dd95eaf222822ab33c
// https://www.exchangerate-api.com/docs/pair-conversion-requests
// https://v6.exchangerate-api.com/v6/2c4d16dd95eaf222822ab33c/latest/USD
// https://v6.exchangerate-api.com/v6/2c4d16dd95eaf222822ab33c/pair/USD/EUR

import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RateProviderAPI {
    @GET("pair/{baseCurrency}/{targetCurrency}")
    fun rate(@Path("baseCurrency") baseCurrency: String, @Path("targetCurrency") targetCurrency: String): Call<ExchangeRateDTO>
}


/**
 * This will return the exchange rate from your base code to the other currency you supplied:
 *
 * {
 * 	"result": "success",
 * 	"documentation": "https://www.exchangerate-api.com/docs",
 * 	"terms_of_use": "https://www.exchangerate-api.com/terms",
 * 	"time_last_update_unix": 1585267200,
 * 	"time_last_update_utc": "Fri, 27 Mar 2020 00:00:00 +0000",
 * 	"time_next_update_unix": 1585270800,
 * 	"time_next_update_utc": "Sat, 28 Mar 2020 01:00:00 +0000",
 * 	"base_code": "EUR",
 * 	"target_code": "GBP",
 * 	"conversion_rate": 0.8412
 * }
 */

data class ExchangeRateDTO(
    @SerializedName("result")
    val result: String="failure",
    @SerializedName("documentation")
    val documentation: String="",
    @SerializedName("terms_of_use")
    val termsOfUse: String="",
    @SerializedName("time_last_update_unix")
    val timeLastUpdateUnix: Long=0,
    @SerializedName("time_last_update_utc")
    val timeLastUpdateUTC: String="",
    @SerializedName("time_next_update_unix")
    val timeNextUpdateUnix: Long=0,
    @SerializedName("time_next_update_utc")
    val timeNextUpdateUTC: String="",
    @SerializedName("base_code")
    val baseCode: String="ERROR",
    @SerializedName("target_code")
    val targetCode: String="ERROR",
    @SerializedName("conversion_rate")
    val conversionRate: Double=-1.0
)

fun ExchangeRateDTO.toModel() = Rate(
    baseCurrency = baseCode,
    targetCurrency = targetCode,
    conversionRate = conversionRate
)

interface CurrencyRateRepository {
    suspend fun rate(baseCurrency: String,targetCurrency: String): Rate
}

class Rate(
    val baseCurrency: String = CURRENCY_ERROR,
    val targetCurrency: String = CURRENCY_ERROR,
    val conversionRate: Double = RATE_ERROR
){
    companion object{
        const val RATE_ERROR = -1.0
        const val CURRENCY_ERROR = "ERROR"
    }
    override fun toString(): String {
        return "Rate(baseCode='$baseCurrency', targetCode='$targetCurrency', conversionRate=$conversionRate)"
    }
}


class CurrencyRateRepositoryImpl: CurrencyRateRepository {

    private val BASE_URL = "https://v6.exchangerate-api.com/v6/2c4d16dd95eaf222822ab33c/"

    private val rateProviderAPI: RateProviderAPI

    init {
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val mOkHttpClient = OkHttpClient
            .Builder()
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

}