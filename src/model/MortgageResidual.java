package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MortgageResidual
{
    private final BigDecimal amount;

    private final BigDecimal duration;

    public MortgageResidual(BigDecimal amount, BigDecimal duration)
    {
        this.amount = amount;
        this.duration = duration;
    }

    public BigDecimal getResidualAmount()
    {
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getResidualDuration()
    {
        return duration;
    }


}
