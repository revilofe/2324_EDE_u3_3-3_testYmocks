import es.iesra.exchange.ExchangeService
import es.iesra.exchange.domain.model.Money
import kotlin.system.exitProcess

fun main()
{
    val exchangeService = ExchangeService()
    val money = exchangeService.exchange(Money(100.0,"EUR"),"USD")
    println(money)
    exitProcess(0)
}

