package es.iesra.exchange

// https://medium.com/@jeremy.leyvraz/kotlin-simplify-your-api-calls-with-elegance-with-retrofit-1be6da7adae4
// https://app.exchangerate-api.com/keys
// key 2c4d16dd95eaf222822ab33c
// https://www.exchangerate-api.com/docs/pair-conversion-requests
// https://v6.exchangerate-api.com/v6/2c4d16dd95eaf222822ab33c/latest/USD
// https://v6.exchangerate-api.com/v6/2c4d16dd95eaf222822ab33c/pair/USD/EUR


import es.iesra.exchange.data.CurrencyRateRepositoryImpl
import es.iesra.exchange.domain.CurrencyRateRepository
import es.iesra.exchange.domain.model.Money
import es.iesra.exchange.domain.model.Rate
import kotlinx.coroutines.runBlocking

class ExchangeService(private var currencyRateRepository: CurrencyRateRepository = CurrencyRateRepositoryImpl()) {
    fun exchange(money: Money, currencyTarget:String) : Money? {
        var moneyResult : Money? = null

        runBlocking {
            val rate = currencyRateRepository.rate(money.currency,currencyTarget)
            if (rate.conversionRate != Rate.RATE_ERROR)
                moneyResult = money.multiplyBy(rate.conversionRate)
        }

        return moneyResult
    }
}