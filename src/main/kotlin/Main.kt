import es.iesra.exchante.rate.CurrencyRateRepository
import es.iesra.exchante.rate.CurrencyRateRepositoryImpl
import es.iesra.exchante.rate.ExchangeService
import es.iesra.model.Money
import kotlin.system.exitProcess

fun main()
{

    var exchangeService = ExchangeService()
    val money = exchangeService.exchange(Money(100.0,"EUR"),"USD")
    println(money)
    exitProcess(0)
}

