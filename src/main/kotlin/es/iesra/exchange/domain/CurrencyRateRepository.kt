package es.iesra.exchange.domain

import es.iesra.exchange.domain.model.Rate

interface CurrencyRateRepository {
    suspend fun rate(baseCurrency: String,targetCurrency: String): Rate
}


