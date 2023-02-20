package model;

import java.math.BigDecimal;

public class MortgageResidiual
{
    private final BigDecimal amount;

    private final BigDecimal duration;

    public MortgageResidiual(BigDecimal amount, BigDecimal duration)
    {
        this.amount = amount;
        this.duration = duration;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public BigDecimal getDuration()
    {
        return duration;
    }

    @Override
    public String toString()
    {
        return "MortgageResidiual{" +
                "amount=" + amount +
                ", duration=" + duration +
                '}';
    }
}
