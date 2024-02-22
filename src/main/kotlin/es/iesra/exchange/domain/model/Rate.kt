package es.iesra.exchange.domain.model

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