package es.iesra.exchante.rate

import es.iesra.model.Money
import kotlinx.coroutines.runBlocking

class ExchangeService(var currencyRateRepository: CurrencyRateRepository = CurrencyRateRepositoryImpl()) {
    fun exchange(money: Money, currencyTarget:String) : Money? {
        var moneyResult : Money? = null
        lateinit var rate: Rate


        runBlocking {
            val rate = currencyRateRepository.rate(money.currency,currencyTarget)
            if (rate.conversionRate != Rate.RATE_ERROR)
                moneyResult = money.multiplyBy(rate.conversionRate)
        }

        return moneyResult
    }
}