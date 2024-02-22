package es.iesra.exchange.data

import com.google.gson.annotations.SerializedName
import es.iesra.exchange.domain.model.Rate

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