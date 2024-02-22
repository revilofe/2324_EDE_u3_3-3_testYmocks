package es.iesra.model

class Money(val cantidad: Double = 0.0, val currency: String = "")
{
    fun add(money: Money): Money
    {
        if (currency != money.currency)
        {
            throw IllegalArgumentException("Currency mismatch")
        }
        return Money(cantidad + money.cantidad, currency)
    }
    fun substract(money: Money): Money
    {
        if (currency != money.currency)
        {
            throw IllegalArgumentException("Currency mismatch")
        }
        return Money(cantidad - money.cantidad, currency)
    }
    fun multiplyBy(factor: Double): Money
    {
        return Money(cantidad * factor, currency)
    }
    fun divideBy(factor: Double): Money
    {
        return Money(cantidad / factor, currency)
    }
    fun isGreaterThan(money: Money): Boolean
    {
        if (currency != money.currency)
        {
            throw IllegalArgumentException("Currency mismatch")
        }
        return cantidad > money.cantidad
    }
    fun isLessThan(money: Money): Boolean
    {
        if (currency != money.currency)
        {
            throw IllegalArgumentException("Currency mismatch")
        }
        return cantidad < money.cantidad
    }
    fun isEqualTo(money: Money): Boolean
    {
        if (currency != money.currency)
        {
            throw IllegalArgumentException("Currency mismatch")
        }
        return cantidad == money.cantidad
    }
    fun isGreaterThanEqualTo(money: Money): Boolean
    {
        if (currency != money.currency)
        {
            throw IllegalArgumentException("Currency mismatch")
        }
        return cantidad >= money.cantidad
    }
    fun isLessThanEqualTo(money: Money): Boolean
    {
        if (currency != money.currency)
        {
            throw IllegalArgumentException("Currency mismatch")
        }
        return cantidad <= money.cantidad
    }
    fun isNotEqualTo(money: Money): Boolean
    {
        if (currency != money.currency)
        {
            throw IllegalArgumentException("Currency mismatch")
        }
        return cantidad != money.cantidad
    }
    override fun toString(): String
    {
        return "$cantidad $currency"
    }

}
